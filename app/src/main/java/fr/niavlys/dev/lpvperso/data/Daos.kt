package fr.niavlys.dev.lpvperso.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_items ORDER BY category, name")
    fun observeAll(): Flow<List<StockItemEntity>>

    @Query("SELECT * FROM stock_items ORDER BY category, name")
    suspend fun getAll(): List<StockItemEntity>

    @Query("SELECT * FROM stock_items WHERE id = :id")
    suspend fun getById(id: Long): StockItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: StockItemEntity): Long

    @Update
    suspend fun update(item: StockItemEntity)

    @Query("DELETE FROM stock_items WHERE id = :id")
    suspend fun delete(id: Long)
}

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY updatedAt DESC")
    fun observeRecipes(): Flow<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM recipes ORDER BY updatedAt DESC")
    suspend fun getRecipesWithIngredients(): List<RecipeWithIngredients>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeWithIngredients(id: Long): RecipeWithIngredients?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecipe(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertIngredient(ingredient: RecipeIngredientEntity): Long

    @Query("DELETE FROM recipe_ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredients(recipeId: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipe(id: Long)
}

@Dao
interface OrderDao {
    @Transaction
    @Query("SELECT * FROM orders ORDER BY orderedAt DESC")
    fun observeOrders(): Flow<List<OrderWithItems>>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY orderedAt DESC")
    suspend fun getOrdersWithItems(): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrderWithItems(id: Long): OrderWithItems?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOrderItem(item: OrderItemEntity): Long
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM price_settings WHERE id = 1")
    fun observePriceSettings(): Flow<PriceSettingsEntity?>

    @Query("SELECT * FROM app_settings WHERE id = 1")
    fun observeAppSettings(): Flow<AppSettingsEntity?>

    @Query("SELECT * FROM price_settings WHERE id = 1")
    suspend fun getPriceSettings(): PriceSettingsEntity?

    @Query("SELECT * FROM app_settings WHERE id = 1")
    suspend fun getAppSettings(): AppSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePriceSettings(settings: PriceSettingsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppSettings(settings: AppSettingsEntity)
}

@Dao
interface MaintenanceDao {
    @Query("DELETE FROM order_items")
    suspend fun deleteOrderItems()

    @Query("DELETE FROM orders")
    suspend fun deleteOrders()

    @Query("DELETE FROM recipe_ingredients")
    suspend fun deleteRecipeIngredients()

    @Query("DELETE FROM recipes")
    suspend fun deleteRecipes()

    @Query("DELETE FROM stock_items")
    suspend fun deleteStockItems()
}
