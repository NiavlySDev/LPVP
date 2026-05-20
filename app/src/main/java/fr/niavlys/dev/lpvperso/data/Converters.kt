package fr.niavlys.dev.lpvperso.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun unitToString(value: UnitType): String = value.name

    @TypeConverter
    fun stringToUnit(value: String): UnitType = UnitType.valueOf(value)

    @TypeConverter
    fun themeToString(value: ThemeMode): String = value.name

    @TypeConverter
    fun stringToTheme(value: String): ThemeMode = ThemeMode.valueOf(value)

    @TypeConverter
    fun statusToString(value: OrderStatus): String = value.name

    @TypeConverter
    fun stringToStatus(value: String): OrderStatus = OrderStatus.valueOf(value)

    @TypeConverter
    fun kindToString(value: StockKind): String = value.name

    @TypeConverter
    fun stringToKind(value: String): StockKind = StockKind.valueOf(value)
}
