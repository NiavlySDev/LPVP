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
public class RecipeDao_Impl(
  __db: RoomDatabase,
) : RecipeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfRecipeEntity: EntityInsertAdapter<RecipeEntity>

  private val __insertAdapterOfRecipeIngredientEntity: EntityInsertAdapter<RecipeIngredientEntity>

  private val __converters: Converters = Converters()
  init {
    this.__db = __db
    this.__insertAdapterOfRecipeEntity = object : EntityInsertAdapter<RecipeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `recipes` (`id`,`name`,`notes`,`baseMl`,`nicotineMl`,`aromaStockItemId`,`aromaMl`,`bottleStockItemId`,`bottleCount`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RecipeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.notes)
        statement.bindDouble(4, entity.baseMl)
        statement.bindDouble(5, entity.nicotineMl)
        val _tmpAromaStockItemId: Long? = entity.aromaStockItemId
        if (_tmpAromaStockItemId == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpAromaStockItemId)
        }
        statement.bindDouble(7, entity.aromaMl)
        val _tmpBottleStockItemId: Long? = entity.bottleStockItemId
        if (_tmpBottleStockItemId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpBottleStockItemId)
        }
        statement.bindLong(9, entity.bottleCount.toLong())
        statement.bindLong(10, entity.createdAt)
        statement.bindLong(11, entity.updatedAt)
      }
    }
    this.__insertAdapterOfRecipeIngredientEntity = object :
        EntityInsertAdapter<RecipeIngredientEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `recipe_ingredients` (`id`,`recipeId`,`stockItemId`,`quantity`,`unit`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RecipeIngredientEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.recipeId)
        statement.bindLong(3, entity.stockItemId)
        statement.bindDouble(4, entity.quantity)
        val _tmp: String = __converters.unitToString(entity.unit)
        statement.bindText(5, _tmp)
      }
    }
  }

  public override suspend fun upsertRecipe(recipe: RecipeEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfRecipeEntity.insertAndReturnId(_connection, recipe)
    _result
  }

  public override suspend fun upsertIngredient(ingredient: RecipeIngredientEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfRecipeIngredientEntity.insertAndReturnId(_connection,
        ingredient)
    _result
  }

  public override fun observeRecipes(): Flow<List<RecipeEntity>> {
    val _sql: String = "SELECT * FROM recipes ORDER BY updatedAt DESC"
    return createFlow(__db, false, arrayOf("recipes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfBaseMl: Int = getColumnIndexOrThrow(_stmt, "baseMl")
        val _columnIndexOfNicotineMl: Int = getColumnIndexOrThrow(_stmt, "nicotineMl")
        val _columnIndexOfAromaStockItemId: Int = getColumnIndexOrThrow(_stmt, "aromaStockItemId")
        val _columnIndexOfAromaMl: Int = getColumnIndexOrThrow(_stmt, "aromaMl")
        val _columnIndexOfBottleStockItemId: Int = getColumnIndexOrThrow(_stmt, "bottleStockItemId")
        val _columnIndexOfBottleCount: Int = getColumnIndexOrThrow(_stmt, "bottleCount")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<RecipeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RecipeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          val _tmpBaseMl: Double
          _tmpBaseMl = _stmt.getDouble(_columnIndexOfBaseMl)
          val _tmpNicotineMl: Double
          _tmpNicotineMl = _stmt.getDouble(_columnIndexOfNicotineMl)
          val _tmpAromaStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfAromaStockItemId)) {
            _tmpAromaStockItemId = null
          } else {
            _tmpAromaStockItemId = _stmt.getLong(_columnIndexOfAromaStockItemId)
          }
          val _tmpAromaMl: Double
          _tmpAromaMl = _stmt.getDouble(_columnIndexOfAromaMl)
          val _tmpBottleStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfBottleStockItemId)) {
            _tmpBottleStockItemId = null
          } else {
            _tmpBottleStockItemId = _stmt.getLong(_columnIndexOfBottleStockItemId)
          }
          val _tmpBottleCount: Int
          _tmpBottleCount = _stmt.getLong(_columnIndexOfBottleCount).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              RecipeEntity(_tmpId,_tmpName,_tmpNotes,_tmpBaseMl,_tmpNicotineMl,_tmpAromaStockItemId,_tmpAromaMl,_tmpBottleStockItemId,_tmpBottleCount,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRecipesWithIngredients(): List<RecipeWithIngredients> {
    val _sql: String = "SELECT * FROM recipes ORDER BY updatedAt DESC"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfBaseMl: Int = getColumnIndexOrThrow(_stmt, "baseMl")
        val _columnIndexOfNicotineMl: Int = getColumnIndexOrThrow(_stmt, "nicotineMl")
        val _columnIndexOfAromaStockItemId: Int = getColumnIndexOrThrow(_stmt, "aromaStockItemId")
        val _columnIndexOfAromaMl: Int = getColumnIndexOrThrow(_stmt, "aromaMl")
        val _columnIndexOfBottleStockItemId: Int = getColumnIndexOrThrow(_stmt, "bottleStockItemId")
        val _columnIndexOfBottleCount: Int = getColumnIndexOrThrow(_stmt, "bottleCount")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionIngredients: LongSparseArray<MutableList<RecipeIngredientEntity>> =
            LongSparseArray<MutableList<RecipeIngredientEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionIngredients.containsKey(_tmpKey)) {
            _collectionIngredients.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshiprecipeIngredientsAsfrNiavlysDevLpvpersoDataRecipeIngredientEntity(_connection,
            _collectionIngredients)
        val _result: MutableList<RecipeWithIngredients> = mutableListOf()
        while (_stmt.step()) {
          val _item: RecipeWithIngredients
          val _tmpRecipe: RecipeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          val _tmpBaseMl: Double
          _tmpBaseMl = _stmt.getDouble(_columnIndexOfBaseMl)
          val _tmpNicotineMl: Double
          _tmpNicotineMl = _stmt.getDouble(_columnIndexOfNicotineMl)
          val _tmpAromaStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfAromaStockItemId)) {
            _tmpAromaStockItemId = null
          } else {
            _tmpAromaStockItemId = _stmt.getLong(_columnIndexOfAromaStockItemId)
          }
          val _tmpAromaMl: Double
          _tmpAromaMl = _stmt.getDouble(_columnIndexOfAromaMl)
          val _tmpBottleStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfBottleStockItemId)) {
            _tmpBottleStockItemId = null
          } else {
            _tmpBottleStockItemId = _stmt.getLong(_columnIndexOfBottleStockItemId)
          }
          val _tmpBottleCount: Int
          _tmpBottleCount = _stmt.getLong(_columnIndexOfBottleCount).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _tmpRecipe =
              RecipeEntity(_tmpId,_tmpName,_tmpNotes,_tmpBaseMl,_tmpNicotineMl,_tmpAromaStockItemId,_tmpAromaMl,_tmpBottleStockItemId,_tmpBottleCount,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpIngredientsCollection: MutableList<RecipeIngredientEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpIngredientsCollection = checkNotNull(_collectionIngredients.get(_tmpKey_1))
          _item = RecipeWithIngredients(_tmpRecipe,_tmpIngredientsCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRecipeWithIngredients(id: Long): RecipeWithIngredients? {
    val _sql: String = "SELECT * FROM recipes WHERE id = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfBaseMl: Int = getColumnIndexOrThrow(_stmt, "baseMl")
        val _columnIndexOfNicotineMl: Int = getColumnIndexOrThrow(_stmt, "nicotineMl")
        val _columnIndexOfAromaStockItemId: Int = getColumnIndexOrThrow(_stmt, "aromaStockItemId")
        val _columnIndexOfAromaMl: Int = getColumnIndexOrThrow(_stmt, "aromaMl")
        val _columnIndexOfBottleStockItemId: Int = getColumnIndexOrThrow(_stmt, "bottleStockItemId")
        val _columnIndexOfBottleCount: Int = getColumnIndexOrThrow(_stmt, "bottleCount")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionIngredients: LongSparseArray<MutableList<RecipeIngredientEntity>> =
            LongSparseArray<MutableList<RecipeIngredientEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionIngredients.containsKey(_tmpKey)) {
            _collectionIngredients.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshiprecipeIngredientsAsfrNiavlysDevLpvpersoDataRecipeIngredientEntity(_connection,
            _collectionIngredients)
        val _result: RecipeWithIngredients?
        if (_stmt.step()) {
          val _tmpRecipe: RecipeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          val _tmpBaseMl: Double
          _tmpBaseMl = _stmt.getDouble(_columnIndexOfBaseMl)
          val _tmpNicotineMl: Double
          _tmpNicotineMl = _stmt.getDouble(_columnIndexOfNicotineMl)
          val _tmpAromaStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfAromaStockItemId)) {
            _tmpAromaStockItemId = null
          } else {
            _tmpAromaStockItemId = _stmt.getLong(_columnIndexOfAromaStockItemId)
          }
          val _tmpAromaMl: Double
          _tmpAromaMl = _stmt.getDouble(_columnIndexOfAromaMl)
          val _tmpBottleStockItemId: Long?
          if (_stmt.isNull(_columnIndexOfBottleStockItemId)) {
            _tmpBottleStockItemId = null
          } else {
            _tmpBottleStockItemId = _stmt.getLong(_columnIndexOfBottleStockItemId)
          }
          val _tmpBottleCount: Int
          _tmpBottleCount = _stmt.getLong(_columnIndexOfBottleCount).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _tmpRecipe =
              RecipeEntity(_tmpId,_tmpName,_tmpNotes,_tmpBaseMl,_tmpNicotineMl,_tmpAromaStockItemId,_tmpAromaMl,_tmpBottleStockItemId,_tmpBottleCount,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpIngredientsCollection: MutableList<RecipeIngredientEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpIngredientsCollection = checkNotNull(_collectionIngredients.get(_tmpKey_1))
          _result = RecipeWithIngredients(_tmpRecipe,_tmpIngredientsCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteIngredients(recipeId: Long) {
    val _sql: String = "DELETE FROM recipe_ingredients WHERE recipeId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, recipeId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteRecipe(id: Long) {
    val _sql: String = "DELETE FROM recipes WHERE id = ?"
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

  private
      fun __fetchRelationshiprecipeIngredientsAsfrNiavlysDevLpvpersoDataRecipeIngredientEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<RecipeIngredientEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshiprecipeIngredientsAsfrNiavlysDevLpvpersoDataRecipeIngredientEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`recipeId`,`stockItemId`,`quantity`,`unit` FROM `recipe_ingredients` WHERE `recipeId` IN (")
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
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "recipeId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfRecipeId: Int = 1
      val _columnIndexOfStockItemId: Int = 2
      val _columnIndexOfQuantity: Int = 3
      val _columnIndexOfUnit: Int = 4
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<RecipeIngredientEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: RecipeIngredientEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpRecipeId: Long
          _tmpRecipeId = _stmt.getLong(_columnIndexOfRecipeId)
          val _tmpStockItemId: Long
          _tmpStockItemId = _stmt.getLong(_columnIndexOfStockItemId)
          val _tmpQuantity: Double
          _tmpQuantity = _stmt.getDouble(_columnIndexOfQuantity)
          val _tmpUnit: UnitType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfUnit)
          _tmpUnit = __converters.stringToUnit(_tmp)
          _item_1 =
              RecipeIngredientEntity(_tmpId,_tmpRecipeId,_tmpStockItemId,_tmpQuantity,_tmpUnit)
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
