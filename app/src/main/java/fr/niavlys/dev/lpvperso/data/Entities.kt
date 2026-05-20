package fr.niavlys.dev.lpvperso.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class UnitType {
    ML,
    UNIT,
}

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
}

enum class OrderStatus {
    COMMANDEE,
    RECUE,
}

enum class StockKind {
    BASE,
    NICOTINE,
    AROME,
    FIOLE,
    AUTRE,
}

@Entity(tableName = "stock_items")
data class StockItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String = "Autres",
    val unit: UnitType = UnitType.ML,
    val quantity: Double = 0.0,
    val purchasePrice: Double? = null,
    val lowStockThreshold: Double? = null,
    val kind: StockKind = StockKind.AUTRE,
    val bottleVolumeMl: Double? = null,
    val packMultiplier: Int = 1,
    val unitPrice: Double? = null,
    val bottleType: String = "",
)

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val notes: String = "",
    val baseMl: Double = 0.0,
    val nicotineMl: Double = 0.0,
    val aromaStockItemId: Long? = null,
    val aromaMl: Double = 0.0,
    val bottleStockItemId: Long? = null,
    val bottleCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

@Entity(
    tableName = "recipe_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = StockItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["stockItemId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("recipeId"), Index("stockItemId")],
)
data class RecipeIngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val stockItemId: Long,
    val quantity: Double,
    val unit: UnitType,
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderedAt: Long = System.currentTimeMillis(),
    val receivedAt: Long? = null,
    val status: OrderStatus = OrderStatus.COMMANDEE,
)

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = StockItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["stockItemId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("orderId"), Index("stockItemId")],
)
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val stockItemId: Long,
    val quantity: Double,
    val unit: UnitType,
)

@Entity(tableName = "price_settings")
data class PriceSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val basePricePerMl: Double = 9.90 / 1000.0,
    val nicotinePricePerMl: Double = 7.50 / 100.0,
    val bottlePrice: Double = 0.0,
    val defaultFees: Double = 0.0,
    val defaultMarginPercent: Double = 30.0,
)

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val diyCalculatorUrl: String = "https://www.lepetitvapoteur.com/fr/calculateur-diy",
)
