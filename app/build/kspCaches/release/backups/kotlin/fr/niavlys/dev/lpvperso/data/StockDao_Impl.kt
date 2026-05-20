package fr.niavlys.dev.lpvperso.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class StockDao_Impl(
  __db: RoomDatabase,
) : StockDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStockItemEntity: EntityInsertAdapter<StockItemEntity>

  private val __converters: Converters = Converters()

  private val __updateAdapterOfStockItemEntity: EntityDeleteOrUpdateAdapter<StockItemEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfStockItemEntity = object : EntityInsertAdapter<StockItemEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `stock_items` (`id`,`name`,`category`,`unit`,`quantity`,`purchasePrice`,`lowStockThreshold`,`kind`,`bottleVolumeMl`,`packMultiplier`,`unitPrice`,`bottleType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StockItemEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.category)
        val _tmp: String = __converters.unitToString(entity.unit)
        statement.bindText(4, _tmp)
        statement.bindDouble(5, entity.quantity)
        val _tmpPurchasePrice: Double? = entity.purchasePrice
        if (_tmpPurchasePrice == null) {
          statement.bindNull(6)
        } else {
          statement.bindDouble(6, _tmpPurchasePrice)
        }
        val _tmpLowStockThreshold: Double? = entity.lowStockThreshold
        if (_tmpLowStockThreshold == null) {
          statement.bindNull(7)
        } else {
          statement.bindDouble(7, _tmpLowStockThreshold)
        }
        val _tmp_1: String = __converters.kindToString(entity.kind)
        statement.bindText(8, _tmp_1)
        val _tmpBottleVolumeMl: Double? = entity.bottleVolumeMl
        if (_tmpBottleVolumeMl == null) {
          statement.bindNull(9)
        } else {
          statement.bindDouble(9, _tmpBottleVolumeMl)
        }
        statement.bindLong(10, entity.packMultiplier.toLong())
        val _tmpUnitPrice: Double? = entity.unitPrice
        if (_tmpUnitPrice == null) {
          statement.bindNull(11)
        } else {
          statement.bindDouble(11, _tmpUnitPrice)
        }
        statement.bindText(12, entity.bottleType)
      }
    }
    this.__updateAdapterOfStockItemEntity = object : EntityDeleteOrUpdateAdapter<StockItemEntity>()
        {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `stock_items` SET `id` = ?,`name` = ?,`category` = ?,`unit` = ?,`quantity` = ?,`purchasePrice` = ?,`lowStockThreshold` = ?,`kind` = ?,`bottleVolumeMl` = ?,`packMultiplier` = ?,`unitPrice` = ?,`bottleType` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: StockItemEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.category)
        val _tmp: String = __converters.unitToString(entity.unit)
        statement.bindText(4, _tmp)
        statement.bindDouble(5, entity.quantity)
        val _tmpPurchasePrice: Double? = entity.purchasePrice
        if (_tmpPurchasePrice == null) {
          statement.bindNull(6)
        } else {
          statement.bindDouble(6, _tmpPurchasePrice)
        }
        val _tmpLowStockThreshold: Double? = entity.lowStockThreshold
        if (_tmpLowStockThreshold == null) {
          statement.bindNull(7)
        } else {
          statement.bindDouble(7, _tmpLowStockThreshold)
        }
        val _tmp_1: String = __converters.kindToString(entity.kind)
        statement.bindText(8, _tmp_1)
        val _tmpBottleVolumeMl: Double? = entity.bottleVolumeMl
        if (_tmpBottleVolumeMl == null) {
          statement.bindNull(9)
        } else {
          statement.bindDouble(9, _tmpBottleVolumeMl)
        }
        statement.bindLong(10, entity.packMultiplier.toLong())
        val _tmpUnitPrice: Double? = entity.unitPrice
        if (_tmpUnitPrice == null) {
          statement.bindNull(11)
        } else {
          statement.bindDouble(11, _tmpUnitPrice)
        }
        statement.bindText(12, entity.bottleType)
        statement.bindLong(13, entity.id)
      }
    }
  }

  public override suspend fun upsert(item: StockItemEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfStockItemEntity.insertAndReturnId(_connection, item)
    _result
  }

  public override suspend fun update(item: StockItemEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfStockItemEntity.handle(_connection, item)
  }

  public override fun observeAll(): Flow<List<StockItemEntity>> {
    val _sql: String = "SELECT * FROM stock_items ORDER BY category, name"
    return createFlow(__db, false, arrayOf("stock_items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfPurchasePrice: Int = getColumnIndexOrThrow(_stmt, "purchasePrice")
        val _columnIndexOfLowStockThreshold: Int = getColumnIndexOrThrow(_stmt, "lowStockThreshold")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfBottleVolumeMl: Int = getColumnIndexOrThrow(_stmt, "bottleVolumeMl")
        val _columnIndexOfPackMultiplier: Int = getColumnIndexOrThrow(_stmt, "packMultiplier")
        val _columnIndexOfUnitPrice: Int = getColumnIndexOrThrow(_stmt, "unitPrice")
        val _columnIndexOfBottleType: Int = getColumnIndexOrThrow(_stmt, "bottleType")
        val _result: MutableList<StockItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StockItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpUnit: UnitType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfUnit)
          _tmpUnit = __converters.stringToUnit(_tmp)
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpPurchasePrice: Double?
          if (_stmt.isNull(_columnIndexOfPurchasePrice)) {
            _tmpPurchasePrice = null
          } else {
            _tmpPurchasePrice = _stmt.getDouble(_columnIndexOfPurchasePrice)
          }
          val _tmpLowStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfLowStockThreshold)) {
            _tmpLowStockThreshold = null
          } else {
            _tmpLowStockThreshold = _stmt.getDouble(_columnIndexOfLowStockThreshold)
          }
          val _tmpKind: StockKind
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfKind)
          _tmpKind = __converters.stringToKind(_tmp_1)
          val _tmpBottleVolumeMl: Double?
          if (_stmt.isNull(_columnIndexOfBottleVolumeMl)) {
            _tmpBottleVolumeMl = null
          } else {
            _tmpBottleVolumeMl = _stmt.getDouble(_columnIndexOfBottleVolumeMl)
          }
          val _tmpPackMultiplier: Int
          _tmpPackMultiplier = _stmt.getLong(_columnIndexOfPackMultiplier).toInt()
          val _tmpUnitPrice: Double?
          if (_stmt.isNull(_columnIndexOfUnitPrice)) {
            _tmpUnitPrice = null
          } else {
            _tmpUnitPrice = _stmt.getDouble(_columnIndexOfUnitPrice)
          }
          val _tmpBottleType: String
          _tmpBottleType = _stmt.getText(_columnIndexOfBottleType)
          _item =
              StockItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpUnit,_tmpQuantity,_tmpPurchasePrice,_tmpLowStockThreshold,_tmpKind,_tmpBottleVolumeMl,_tmpPackMultiplier,_tmpUnitPrice,_tmpBottleType)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<StockItemEntity> {
    val _sql: String = "SELECT * FROM stock_items ORDER BY category, name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfPurchasePrice: Int = getColumnIndexOrThrow(_stmt, "purchasePrice")
        val _columnIndexOfLowStockThreshold: Int = getColumnIndexOrThrow(_stmt, "lowStockThreshold")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfBottleVolumeMl: Int = getColumnIndexOrThrow(_stmt, "bottleVolumeMl")
        val _columnIndexOfPackMultiplier: Int = getColumnIndexOrThrow(_stmt, "packMultiplier")
        val _columnIndexOfUnitPrice: Int = getColumnIndexOrThrow(_stmt, "unitPrice")
        val _columnIndexOfBottleType: Int = getColumnIndexOrThrow(_stmt, "bottleType")
        val _result: MutableList<StockItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StockItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpUnit: UnitType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfUnit)
          _tmpUnit = __converters.stringToUnit(_tmp)
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpPurchasePrice: Double?
          if (_stmt.isNull(_columnIndexOfPurchasePrice)) {
            _tmpPurchasePrice = null
          } else {
            _tmpPurchasePrice = _stmt.getDouble(_columnIndexOfPurchasePrice)
          }
          val _tmpLowStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfLowStockThreshold)) {
            _tmpLowStockThreshold = null
          } else {
            _tmpLowStockThreshold = _stmt.getDouble(_columnIndexOfLowStockThreshold)
          }
          val _tmpKind: StockKind
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfKind)
          _tmpKind = __converters.stringToKind(_tmp_1)
          val _tmpBottleVolumeMl: Double?
          if (_stmt.isNull(_columnIndexOfBottleVolumeMl)) {
            _tmpBottleVolumeMl = null
          } else {
            _tmpBottleVolumeMl = _stmt.getDouble(_columnIndexOfBottleVolumeMl)
          }
          val _tmpPackMultiplier: Int
          _tmpPackMultiplier = _stmt.getLong(_columnIndexOfPackMultiplier).toInt()
          val _tmpUnitPrice: Double?
          if (_stmt.isNull(_columnIndexOfUnitPrice)) {
            _tmpUnitPrice = null
          } else {
            _tmpUnitPrice = _stmt.getDouble(_columnIndexOfUnitPrice)
          }
          val _tmpBottleType: String
          _tmpBottleType = _stmt.getText(_columnIndexOfBottleType)
          _item =
              StockItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpUnit,_tmpQuantity,_tmpPurchasePrice,_tmpLowStockThreshold,_tmpKind,_tmpBottleVolumeMl,_tmpPackMultiplier,_tmpUnitPrice,_tmpBottleType)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): StockItemEntity? {
    val _sql: String = "SELECT * FROM stock_items WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfPurchasePrice: Int = getColumnIndexOrThrow(_stmt, "purchasePrice")
        val _columnIndexOfLowStockThreshold: Int = getColumnIndexOrThrow(_stmt, "lowStockThreshold")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfBottleVolumeMl: Int = getColumnIndexOrThrow(_stmt, "bottleVolumeMl")
        val _columnIndexOfPackMultiplier: Int = getColumnIndexOrThrow(_stmt, "packMultiplier")
        val _columnIndexOfUnitPrice: Int = getColumnIndexOrThrow(_stmt, "unitPrice")
        val _columnIndexOfBottleType: Int = getColumnIndexOrThrow(_stmt, "bottleType")
        val _result: StockItemEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpUnit: UnitType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfUnit)
          _tmpUnit = __converters.stringToUnit(_tmp)
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpPurchasePrice: Double?
          if (_stmt.isNull(_columnIndexOfPurchasePrice)) {
            _tmpPurchasePrice = null
          } else {
            _tmpPurchasePrice = _stmt.getDouble(_columnIndexOfPurchasePrice)
          }
          val _tmpLowStockThreshold: Double?
          if (_stmt.isNull(_columnIndexOfLowStockThreshold)) {
            _tmpLowStockThreshold = null
          } else {
            _tmpLowStockThreshold = _stmt.getDouble(_columnIndexOfLowStockThreshold)
          }
          val _tmpKind: StockKind
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfKind)
          _tmpKind = __converters.stringToKind(_tmp_1)
          val _tmpBottleVolumeMl: Double?
          if (_stmt.isNull(_columnIndexOfBottleVolumeMl)) {
            _tmpBottleVolumeMl = null
          } else {
            _tmpBottleVolumeMl = _stmt.getDouble(_columnIndexOfBottleVolumeMl)
          }
          val _tmpPackMultiplier: Int
          _tmpPackMultiplier = _stmt.getLong(_columnIndexOfPackMultiplier).toInt()
          val _tmpUnitPrice: Double?
          if (_stmt.isNull(_columnIndexOfUnitPrice)) {
            _tmpUnitPrice = null
          } else {
            _tmpUnitPrice = _stmt.getDouble(_columnIndexOfUnitPrice)
          }
          val _tmpBottleType: String
          _tmpBottleType = _stmt.getText(_columnIndexOfBottleType)
          _result =
              StockItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpUnit,_tmpQuantity,_tmpPurchasePrice,_tmpLowStockThreshold,_tmpKind,_tmpBottleVolumeMl,_tmpPackMultiplier,_tmpUnitPrice,_tmpBottleType)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(id: Long) {
    val _sql: String = "DELETE FROM stock_items WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
