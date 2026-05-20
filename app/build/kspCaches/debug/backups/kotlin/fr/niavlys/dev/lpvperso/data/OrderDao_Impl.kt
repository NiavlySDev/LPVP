package fr.niavlys.dev.lpvperso.`data`

import androidx.collection.LongSparseArray
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndex
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchLongSparseArray
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class OrderDao_Impl(
  __db: RoomDatabase,
) : OrderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfOrderEntity: EntityInsertAdapter<OrderEntity>

  private val __converters: Converters = Converters()

  private val __insertAdapterOfOrderItemEntity: EntityInsertAdapter<OrderItemEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfOrderEntity = object : EntityInsertAdapter<OrderEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `orders` (`id`,`orderedAt`,`receivedAt`,`status`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OrderEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.orderedAt)
        val _tmpReceivedAt: Long? = entity.receivedAt
        if (_tmpReceivedAt == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpReceivedAt)
        }
        val _tmp: String = __converters.statusToString(entity.status)
        statement.bindText(4, _tmp)
      }
    }
    this.__insertAdapterOfOrderItemEntity = object : EntityInsertAdapter<OrderItemEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `order_items` (`id`,`orderId`,`stockItemId`,`quantity`,`unit`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OrderItemEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.orderId)
        statement.bindLong(3, entity.stockItemId)
        statement.bindDouble(4, entity.quantity)
        val _tmp: String = __converters.unitToString(entity.unit)
        statement.bindText(5, _tmp)
      }
    }
  }

  public override suspend fun upsertOrder(order: OrderEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfOrderEntity.insertAndReturnId(_connection, order)
    _result
  }

  public override suspend fun upsertOrderItem(item: OrderItemEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfOrderItemEntity.insertAndReturnId(_connection, item)
    _result
  }

  public override fun observeOrders(): Flow<List<OrderWithItems>> {
    val _sql: String = "SELECT * FROM orders ORDER BY orderedAt DESC"
    return createFlow(__db, true, arrayOf("order_items", "orders")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderedAt: Int = getColumnIndexOrThrow(_stmt, "orderedAt")
        val _columnIndexOfReceivedAt: Int = getColumnIndexOrThrow(_stmt, "receivedAt")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _collectionItems: LongSparseArray<MutableList<OrderItemEntity>> =
            LongSparseArray<MutableList<OrderItemEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionItems.containsKey(_tmpKey)) {
            _collectionItems.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshiporderItemsAsfrNiavlysDevLpvpersoDataOrderItemEntity(_connection,
            _collectionItems)
        val _result: MutableList<OrderWithItems> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderWithItems
          val _tmpOrder: OrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpOrderedAt: Long
          _tmpOrderedAt = _stmt.getLong(_columnIndexOfOrderedAt)
          val _tmpReceivedAt: Long?
          if (_stmt.isNull(_columnIndexOfReceivedAt)) {
            _tmpReceivedAt = null
          } else {
            _tmpReceivedAt = _stmt.getLong(_columnIndexOfReceivedAt)
          }
          val _tmpStatus: OrderStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.stringToStatus(_tmp)
          _tmpOrder = OrderEntity(_tmpId,_tmpOrderedAt,_tmpReceivedAt,_tmpStatus)
          val _tmpItemsCollection: MutableList<OrderItemEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpItemsCollection = checkNotNull(_collectionItems.get(_tmpKey_1))
          _item = OrderWithItems(_tmpOrder,_tmpItemsCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getOrdersWithItems(): List<OrderWithItems> {
    val _sql: String = "SELECT * FROM orders ORDER BY orderedAt DESC"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderedAt: Int = getColumnIndexOrThrow(_stmt, "orderedAt")
        val _columnIndexOfReceivedAt: Int = getColumnIndexOrThrow(_stmt, "receivedAt")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _collectionItems: LongSparseArray<MutableList<OrderItemEntity>> =
            LongSparseArray<MutableList<OrderItemEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionItems.containsKey(_tmpKey)) {
            _collectionItems.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshiporderItemsAsfrNiavlysDevLpvpersoDataOrderItemEntity(_connection,
            _collectionItems)
        val _result: MutableList<OrderWithItems> = mutableListOf()
        while (_stmt.step()) {
          val _item: OrderWithItems
          val _tmpOrder: OrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpOrderedAt: Long
          _tmpOrderedAt = _stmt.getLong(_columnIndexOfOrderedAt)
          val _tmpReceivedAt: Long?
          if (_stmt.isNull(_columnIndexOfReceivedAt)) {
            _tmpReceivedAt = null
          } else {
            _tmpReceivedAt = _stmt.getLong(_columnIndexOfReceivedAt)
          }
          val _tmpStatus: OrderStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.stringToStatus(_tmp)
          _tmpOrder = OrderEntity(_tmpId,_tmpOrderedAt,_tmpReceivedAt,_tmpStatus)
          val _tmpItemsCollection: MutableList<OrderItemEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpItemsCollection = checkNotNull(_collectionItems.get(_tmpKey_1))
          _item = OrderWithItems(_tmpOrder,_tmpItemsCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getOrderWithItems(id: Long): OrderWithItems? {
    val _sql: String = "SELECT * FROM orders WHERE id = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfOrderedAt: Int = getColumnIndexOrThrow(_stmt, "orderedAt")
        val _columnIndexOfReceivedAt: Int = getColumnIndexOrThrow(_stmt, "receivedAt")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _collectionItems: LongSparseArray<MutableList<OrderItemEntity>> =
            LongSparseArray<MutableList<OrderItemEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionItems.containsKey(_tmpKey)) {
            _collectionItems.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshiporderItemsAsfrNiavlysDevLpvpersoDataOrderItemEntity(_connection,
            _collectionItems)
        val _result: OrderWithItems?
        if (_stmt.step()) {
          val _tmpOrder: OrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpOrderedAt: Long
          _tmpOrderedAt = _stmt.getLong(_columnIndexOfOrderedAt)
          val _tmpReceivedAt: Long?
          if (_stmt.isNull(_columnIndexOfReceivedAt)) {
            _tmpReceivedAt = null
          } else {
            _tmpReceivedAt = _stmt.getLong(_columnIndexOfReceivedAt)
          }
          val _tmpStatus: OrderStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.stringToStatus(_tmp)
          _tmpOrder = OrderEntity(_tmpId,_tmpOrderedAt,_tmpReceivedAt,_tmpStatus)
          val _tmpItemsCollection: MutableList<OrderItemEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpItemsCollection = checkNotNull(_collectionItems.get(_tmpKey_1))
          _result = OrderWithItems(_tmpOrder,_tmpItemsCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  private
      fun __fetchRelationshiporderItemsAsfrNiavlysDevLpvpersoDataOrderItemEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<OrderItemEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshiporderItemsAsfrNiavlysDevLpvpersoDataOrderItemEntity(_connection, _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`orderId`,`stockItemId`,`quantity`,`unit` FROM `order_items` WHERE `orderId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "orderId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfOrderId: Int = 1
      val _columnIndexOfStockItemId: Int = 2
      val _columnIndexOfQuantity: Int = 3
      val _columnIndexOfUnit: Int = 4
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<OrderItemEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: OrderItemEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpOrderId: Long
          _tmpOrderId = _stmt.getLong(_columnIndexOfOrderId)
          val _tmpStockItemId: Long
          _tmpStockItemId = _stmt.getLong(_columnIndexOfStockItemId)
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: UnitType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfUnit)
          _tmpUnit = __converters.stringToUnit(_tmp)
          _item_1 = OrderItemEntity(_tmpId,_tmpOrderId,_tmpStockItemId,_tmpQuantity,_tmpUnit)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
