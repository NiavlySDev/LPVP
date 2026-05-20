package fr.niavlys.dev.lpvperso.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.niavlys.dev.lpvperso.data.AppRepository
import fr.niavlys.dev.lpvperso.data.AppSettingsEntity
import fr.niavlys.dev.lpvperso.data.OrderItemEntity
import fr.niavlys.dev.lpvperso.data.OrderWithItems
import fr.niavlys.dev.lpvperso.data.PriceSettingsEntity
import fr.niavlys.dev.lpvperso.data.RecipeEntity
import fr.niavlys.dev.lpvperso.data.RecipeIngredientEntity
import fr.niavlys.dev.lpvperso.data.StockKind
import fr.niavlys.dev.lpvperso.data.StockItemEntity
import fr.niavlys.dev.lpvperso.data.ThemeMode
import fr.niavlys.dev.lpvperso.data.UnitType
import fr.niavlys.dev.lpvperso.domain.PriceCalculationInput
import fr.niavlys.dev.lpvperso.domain.PriceCalculationResult
import fr.niavlys.dev.lpvperso.domain.PriceCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiState(
    val stock: List<StockItemEntity> = emptyList(),
    val recipes: List<RecipeEntity> = emptyList(),
    val orders: List<OrderWithItems> = emptyList(),
    val priceSettings: PriceSettingsEntity = PriceSettingsEntity(),
    val appSettings: AppSettingsEntity = AppSettingsEntity(),
    val message: String? = null,
)

class LpvViewModel(private val repository: AppRepository) : ViewModel() {
    private val message = MutableStateFlow<String?>(null)

    private val dataState = combine(
        repository.stockItems,
        repository.recipes,
        repository.orders,
        repository.priceSettings,
        repository.appSettings,
    ) { stock, recipes, orders, priceSettings, appSettings ->
        UiState(
            stock = stock,
            recipes = recipes,
            orders = orders,
            priceSettings = priceSettings ?: PriceSettingsEntity(),
            appSettings = appSettings ?: AppSettingsEntity(),
        )
    }

    val state: StateFlow<UiState> = combine(dataState, message) { state, message ->
        state.copy(message = message)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState())

    fun addStock(
        name: String,
        category: String,
        unit: UnitType,
        quantity: Double,
        threshold: Double?,
        kind: StockKind = StockKind.AUTRE,
        bottleVolumeMl: Double? = null,
        packMultiplier: Int = 1,
        unitPrice: Double? = null,
        bottleType: String = "",
    ) {
        if (name.isBlank()) {
            message.value = "Donne un nom a l'article."
            return
        }
        viewModelScope.launch {
            repository.saveStockItem(
                StockItemEntity(
                    name = name.trim(),
                    category = category.ifBlank { "Autres" }.trim(),
                    unit = unit,
                    quantity = quantity.coerceAtLeast(0.0),
                    lowStockThreshold = threshold,
                    kind = kind,
                    bottleVolumeMl = bottleVolumeMl,
                    packMultiplier = packMultiplier.coerceAtLeast(1),
                    unitPrice = unitPrice,
                    bottleType = bottleType,
                ),
            )
            message.value = "Article ajoute au stock."
        }
    }

    fun changeStock(id: Long, delta: Double) {
        viewModelScope.launch { repository.changeStock(id, delta) }
    }

    fun setStockQuantity(id: Long, quantity: Double) {
        viewModelScope.launch {
            repository.setStockQuantity(id, quantity)
            message.value = "Quantite mise a jour."
        }
    }

    fun resetStock(id: Long) {
        viewModelScope.launch {
            val item = state.value.stock.firstOrNull { it.id == id } ?: return@launch
            repository.saveStockItem(item.copy(quantity = 0.0))
        }
    }

    fun addRecipe(
        name: String,
        baseMl: Double,
        nicotineMl: Double,
        aromaStockItemId: Long?,
        aromaMl: Double,
        bottleStockItemId: Long?,
        bottleCount: Int,
    ) {
        if (name.isBlank()) {
            message.value = "Donne un nom a la recette."
            return
        }
        if (baseMl <= 0.0 && nicotineMl <= 0.0 && aromaMl <= 0.0 && bottleCount <= 0) {
            message.value = "Ajoute au moins une quantite a la recette."
            return
        }
        viewModelScope.launch {
            val stock = state.value.stock
            val base = stock.firstOrNull { it.kind == StockKind.BASE }
            val nicotine = stock.firstOrNull { it.kind == StockKind.NICOTINE }
            val aroma = stock.firstOrNull { it.id == aromaStockItemId }
            val bottle = stock.firstOrNull { it.id == bottleStockItemId }
            val recipe = RecipeEntity(
                name = name.trim(),
                baseMl = baseMl,
                nicotineMl = nicotineMl,
                aromaStockItemId = aromaStockItemId,
                aromaMl = aromaMl,
                bottleStockItemId = bottleStockItemId,
                bottleCount = bottleCount,
            )
            val ingredients = buildList {
                if (base != null && baseMl > 0.0) add(RecipeIngredientEntity(recipeId = 0, stockItemId = base.id, quantity = baseMl, unit = UnitType.ML))
                if (nicotine != null && nicotineMl > 0.0) add(RecipeIngredientEntity(recipeId = 0, stockItemId = nicotine.id, quantity = nicotineMl, unit = UnitType.ML))
                if (aroma != null && aromaMl > 0.0) add(RecipeIngredientEntity(recipeId = 0, stockItemId = aroma.id, quantity = aromaMl, unit = UnitType.ML))
                if (bottle != null && bottleCount > 0) add(RecipeIngredientEntity(recipeId = 0, stockItemId = bottle.id, quantity = bottleCount.toDouble(), unit = UnitType.UNIT))
            }
            repository.saveRecipe(recipe, ingredients)
            message.value = "Recette sauvegardee."
        }
    }

    fun completeRecipe(id: Long) {
        viewModelScope.launch {
            val result = repository.completeRecipe(id)
            message.value = result.fold(
                onSuccess = { "Recette terminee, stock mis a jour." },
                onFailure = { "Stock insuffisant: ${it.message}" },
            )
        }
    }

    fun createOrder(quantities: List<Pair<Long, Double>>) {
        val lines = quantities.filter { it.second > 0.0 }
        if (lines.isEmpty()) {
            message.value = "Ajoute au moins une quantite a la commande."
            return
        }
        viewModelScope.launch {
            val items = lines.mapNotNull { (stockId, qty) ->
                val stock = state.value.stock.firstOrNull { it.id == stockId } ?: return@mapNotNull null
                OrderItemEntity(orderId = 0, stockItemId = stockId, quantity = qty, unit = stock.unit)
            }
            repository.createOrder(items)
            message.value = "Commande enregistree."
        }
    }

    fun receiveOrder(id: Long) {
        viewModelScope.launch {
            val result = repository.receiveOrder(id)
            message.value = result.fold(
                onSuccess = { "Commande recue, stock ajoute." },
                onFailure = { it.message ?: "Impossible de recevoir la commande." },
            )
        }
    }

    fun savePriceSettings(settings: PriceSettingsEntity) {
        viewModelScope.launch { repository.savePriceSettings(settings) }
    }

    fun saveAppSettings(settings: AppSettingsEntity) {
        viewModelScope.launch { repository.saveAppSettings(settings) }
    }

    fun calculate(input: PriceCalculationInput): PriceCalculationResult = PriceCalculator.calculate(input)

    fun exportJson(onExported: (String) -> Unit) {
        viewModelScope.launch { onExported(repository.exportJson()) }
    }

    fun importJson(json: String) {
        if (json.isBlank()) return
        viewModelScope.launch {
            val result = repository.importJson(json)
            message.value = result.fold(
                onSuccess = { "Import termine." },
                onFailure = { "Import impossible: ${it.message}" },
            )
        }
    }

    companion object {
        fun factory(repository: AppRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = LpvViewModel(repository) as T
        }
    }
}
