package fr.niavlys.dev.lpvperso.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        StockItemEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        PriceSettingsEntity::class,
        AppSettingsEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun recipeDao(): RecipeDao
    abstract fun orderDao(): OrderDao
    abstract fun settingsDao(): SettingsDao
    abstract fun maintenanceDao(): MaintenanceDao
}
