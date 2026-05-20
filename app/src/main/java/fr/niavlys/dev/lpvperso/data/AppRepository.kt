package fr.niavlys.dev.lpvperso.data

import androidx.room.withTransaction
import com.google.gson.GsonBuilder
import fr.niavlys.dev.lpvperso.domain.StockLine
import fr.niavlys.dev.lpvperso.domain.StockRules
import kotlinx.coroutines.flow.Flow

class AppRepository(private val db: AppDatabase) {
    private val stockDao = db.stockDao()
    private val recipeDao = db.recipeDao()
    private val orderDao = db.orderDao()
    private val settingsDao = db.settingsDao()
    private val maintenanceDao = db.maintenanceDao()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    val stockItems: Flow<List<StockItemEntity>> = stockDao.observeAll()
    val recipes: Flow<List<RecipeEntity>> = recipeDao.observeRecipes()
    val orders: Flow<List<OrderWithItems>> = orderDao.observeOrders()
    val priceSettings: Flow<PriceSettingsEntity?> = settingsDao.observePriceSettings()
    val appSettings: Flow<AppSettingsEntity?> = settingsDao.observeAppSettings()

    suspend fun saveStockItem(item: StockItemEntity): Long = stockDao.upsert(item)

    suspend fun changeStock(id: Long, delta: Double) = db.withTransaction {
        val item = stockDao.getById(id) ?: return@withTransaction
        stockDao.update(item.copy(quantity = (item.quantity + delta).coerceAtLeast(0.0)))
    }

    suspend fun setStockQuantity(id: Long, quantity: Double) = db.withTransaction {
        val item = stockDao.getById(id) ?: return@withTransaction
        stockDao.update(item.copy(quantity = quantity.coerceAtLeast(0.0)))
    }

    suspend fun deleteStockItem(id: Long) = stockDao.delete(id)

    suspend fun saveRecipe(recipe: RecipeEntity, ingredients: List<RecipeIngredientEntity>): Long =
        db.withTransaction {
            val recipeId = recipeDao.upsertRecipe(recipe.copy(updatedAt = System.currentTimeMillis()))
            recipeDao.deleteIngredients(recipeId)
            ingredients.forEach { recipeDao.upsertIngredient(it.copy(recipeId = recipeId)) }
            recipeId
        }

    suspend fun deleteRecipe(id: Long) = recipeDao.deleteRecipe(id)

    suspend fun completeRecipe(recipeId: Long): Result<Unit> = db.withTransaction {
        val recipe = recipeDao.getRecipeWithIngredients(recipeId)
            ?: return@withTransaction Result.failure(IllegalArgumentException("Recette introuvable"))
        val stockById = stockDao.getAll().associateBy { it.id }
        val lines = recipe.ingredients.mapNotNull { ingredient ->
            stockById[ingredient.stockItemId]?.let {
                StockLine(it.id, it.name, it.quantity, ingredient.quantity)
            }
        }
        val check = StockRules.checkAvailability(lines)
        if (!check.canConsume) {
            val message = check.missing.joinToString { "${it.name}: manque ${it.required - it.available}" }
            return@withTransaction Result.failure(IllegalStateException(message))
        }
        recipe.ingredients.forEach { ingredient ->
            val item = stockById.getValue(ingredient.stockItemId)
            stockDao.update(item.copy(quantity = item.quantity - ingredient.quantity))
        }
        Result.success(Unit)
    }

    suspend fun createOrder(items: List<OrderItemEntity>): Long = db.withTransaction {
        val orderId = orderDao.upsertOrder(OrderEntity())
        items.forEach { orderDao.upsertOrderItem(it.copy(orderId = orderId)) }
        orderId
    }

    suspend fun receiveOrder(orderId: Long): Result<Unit> = db.withTransaction {
        val order = orderDao.getOrderWithItems(orderId)
            ?: return@withTransaction Result.failure(IllegalArgumentException("Commande introuvable"))
        if (order.order.status == OrderStatus.RECUE) {
            return@withTransaction Result.failure(IllegalStateException("Commande deja recue"))
        }
        val stockById = stockDao.getAll().associateBy { it.id }
        order.items.forEach { orderItem ->
            val item = stockById[orderItem.stockItemId] ?: return@forEach
            stockDao.update(item.copy(quantity = item.quantity + item.receivedQuantity(orderItem.quantity)))
        }
        orderDao.upsertOrder(
            order.order.copy(
                status = OrderStatus.RECUE,
                receivedAt = System.currentTimeMillis(),
            ),
        )
        Result.success(Unit)
    }

    suspend fun savePriceSettings(settings: PriceSettingsEntity) = settingsDao.savePriceSettings(settings)

    suspend fun saveAppSettings(settings: AppSettingsEntity) = settingsDao.saveAppSettings(settings)

    suspend fun exportJson(): String {
        val snapshot = ExportSnapshot(
            stockItems = stockDao.getAll(),
            recipes = recipeDao.getRecipesWithIngredients(),
            orders = orderDao.getOrdersWithItems(),
            priceSettings = settingsDao.getPriceSettings() ?: PriceSettingsEntity(),
            appSettings = settingsDao.getAppSettings() ?: AppSettingsEntity(),
        )
        return gson.toJson(snapshot)
    }

    suspend fun importJson(json: String): Result<Unit> = runCatching {
        val snapshot = gson.fromJson(json, ExportSnapshot::class.java)
            ?: error("JSON invalide")
        db.withTransaction {
            maintenanceDao.deleteOrderItems()
            maintenanceDao.deleteOrders()
            maintenanceDao.deleteRecipeIngredients()
            maintenanceDao.deleteRecipes()
            maintenanceDao.deleteStockItems()

            settingsDao.savePriceSettings(snapshot.priceSettings)
            settingsDao.saveAppSettings(snapshot.appSettings)
            snapshot.stockItems.forEach { stockDao.upsert(it) }
            snapshot.recipes.forEach { recipe ->
                recipeDao.upsertRecipe(recipe.recipe)
                recipe.ingredients.forEach { recipeDao.upsertIngredient(it) }
            }
            snapshot.orders.forEach { order ->
                orderDao.upsertOrder(order.order)
                order.items.forEach { orderDao.upsertOrderItem(it) }
            }
        }
    }
}

fun StockItemEntity.receivedQuantity(packageCount: Double): Double {
    val multiplier = packMultiplier.coerceAtLeast(1)
    val volume = bottleVolumeMl ?: 0.0
    return if (unit == UnitType.ML && volume > 0.0) {
        packageCount * multiplier * volume
    } else {
        packageCount
    }
}

data class ExportSnapshot(
    val stockItems: List<StockItemEntity>,
    val recipes: List<RecipeWithIngredients>,
    val orders: List<OrderWithItems>,
    val priceSettings: PriceSettingsEntity,
    val appSettings: AppSettingsEntity,
)
