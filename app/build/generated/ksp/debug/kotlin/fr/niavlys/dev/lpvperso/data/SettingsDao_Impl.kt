package fr.niavlys.dev.lpvperso.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SettingsDao_Impl(
  __db: RoomDatabase,
) : SettingsDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPriceSettingsEntity: EntityInsertAdapter<PriceSettingsEntity>

  private val __insertAdapterOfAppSettingsEntity: EntityInsertAdapter<AppSettingsEntity>

  private val __converters: Converters = Converters()
  init {
    this.__db = __db
    this.__insertAdapterOfPriceSettingsEntity = object : EntityInsertAdapter<PriceSettingsEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `price_settings` (`id`,`basePricePerMl`,`nicotinePricePerMl`,`bottlePrice`,`defaultFees`,`defaultMarginPercent`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PriceSettingsEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.basePricePerMl)
        statement.bindDouble(3, entity.nicotinePricePerMl)
        statement.bindDouble(4, entity.bottlePrice)
        statement.bindDouble(5, entity.defaultFees)
        statement.bindDouble(6, entity.defaultMarginPercent)
      }
    }
    this.__insertAdapterOfAppSettingsEntity = object : EntityInsertAdapter<AppSettingsEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `app_settings` (`id`,`themeMode`,`diyCalculatorUrl`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AppSettingsEntity) {
        statement.bindLong(1, entity.id.toLong())
        val _tmp: String = __converters.themeToString(entity.themeMode)
        statement.bindText(2, _tmp)
        statement.bindText(3, entity.diyCalculatorUrl)
      }
    }
  }

  public override suspend fun savePriceSettings(settings: PriceSettingsEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPriceSettingsEntity.insert(_connection, settings)
  }

  public override suspend fun saveAppSettings(settings: AppSettingsEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfAppSettingsEntity.insert(_connection, settings)
  }

  public override fun observePriceSettings(): Flow<PriceSettingsEntity?> {
    val _sql: String = "SELECT * FROM price_settings WHERE id = 1"
    return createFlow(__db, false, arrayOf("price_settings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfBasePricePerMl: Int = getColumnIndexOrThrow(_stmt, "basePricePerMl")
        val _columnIndexOfNicotinePricePerMl: Int = getColumnIndexOrThrow(_stmt,
            "nicotinePricePerMl")
        val _columnIndexOfBottlePrice: Int = getColumnIndexOrThrow(_stmt, "bottlePrice")
        val _columnIndexOfDefaultFees: Int = getColumnIndexOrThrow(_stmt, "defaultFees")
        val _columnIndexOfDefaultMarginPercent: Int = getColumnIndexOrThrow(_stmt,
            "defaultMarginPercent")
        val _result: PriceSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpBasePricePerMl: Double
          _tmpBasePricePerMl = _stmt.getDouble(_columnIndexOfBasePricePerMl)
          val _tmpNicotinePricePerMl: Double
          _tmpNicotinePricePerMl = _stmt.getDouble(_columnIndexOfNicotinePricePerMl)
          val _tmpBottlePrice: Double
          _tmpBottlePrice = _stmt.getDouble(_columnIndexOfBottlePrice)
          val _tmpDefaultFees: Double
          _tmpDefaultFees = _stmt.getDouble(_columnIndexOfDefaultFees)
          val _tmpDefaultMarginPercent: Double
          _tmpDefaultMarginPercent = _stmt.getDouble(_columnIndexOfDefaultMarginPercent)
          _result =
              PriceSettingsEntity(_tmpId,_tmpBasePricePerMl,_tmpNicotinePricePerMl,_tmpBottlePrice,_tmpDefaultFees,_tmpDefaultMarginPercent)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeAppSettings(): Flow<AppSettingsEntity?> {
    val _sql: String = "SELECT * FROM app_settings WHERE id = 1"
    return createFlow(__db, false, arrayOf("app_settings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfThemeMode: Int = getColumnIndexOrThrow(_stmt, "themeMode")
        val _columnIndexOfDiyCalculatorUrl: Int = getColumnIndexOrThrow(_stmt, "diyCalculatorUrl")
        val _result: AppSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpThemeMode: ThemeMode
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfThemeMode)
          _tmpThemeMode = __converters.stringToTheme(_tmp)
          val _tmpDiyCalculatorUrl: String
          _tmpDiyCalculatorUrl = _stmt.getText(_columnIndexOfDiyCalculatorUrl)
          _result = AppSettingsEntity(_tmpId,_tmpThemeMode,_tmpDiyCalculatorUrl)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPriceSettings(): PriceSettingsEntity? {
    val _sql: String = "SELECT * FROM price_settings WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfBasePricePerMl: Int = getColumnIndexOrThrow(_stmt, "basePricePerMl")
        val _columnIndexOfNicotinePricePerMl: Int = getColumnIndexOrThrow(_stmt,
            "nicotinePricePerMl")
        val _columnIndexOfBottlePrice: Int = getColumnIndexOrThrow(_stmt, "bottlePrice")
        val _columnIndexOfDefaultFees: Int = getColumnIndexOrThrow(_stmt, "defaultFees")
        val _columnIndexOfDefaultMarginPercent: Int = getColumnIndexOrThrow(_stmt,
            "defaultMarginPercent")
        val _result: PriceSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpBasePricePerMl: Double
          _tmpBasePricePerMl = _stmt.getDouble(_columnIndexOfBasePricePerMl)
          val _tmpNicotinePricePerMl: Double
          _tmpNicotinePricePerMl = _stmt.getDouble(_columnIndexOfNicotinePricePerMl)
          val _tmpBottlePrice: Double
          _tmpBottlePrice = _stmt.getDouble(_columnIndexOfBottlePrice)
          val _tmpDefaultFees: Double
          _tmpDefaultFees = _stmt.getDouble(_columnIndexOfDefaultFees)
          val _tmpDefaultMarginPercent: Double
          _tmpDefaultMarginPercent = _stmt.getDouble(_columnIndexOfDefaultMarginPercent)
          _result =
              PriceSettingsEntity(_tmpId,_tmpBasePricePerMl,_tmpNicotinePricePerMl,_tmpBottlePrice,_tmpDefaultFees,_tmpDefaultMarginPercent)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAppSettings(): AppSettingsEntity? {
    val _sql: String = "SELECT * FROM app_settings WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfThemeMode: Int = getColumnIndexOrThrow(_stmt, "themeMode")
        val _columnIndexOfDiyCalculatorUrl: Int = getColumnIndexOrThrow(_stmt, "diyCalculatorUrl")
        val _result: AppSettingsEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpThemeMode: ThemeMode
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfThemeMode)
          _tmpThemeMode = __converters.stringToTheme(_tmp)
          val _tmpDiyCalculatorUrl: String
          _tmpDiyCalculatorUrl = _stmt.getText(_columnIndexOfDiyCalculatorUrl)
          _result = AppSettingsEntity(_tmpId,_tmpThemeMode,_tmpDiyCalculatorUrl)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
