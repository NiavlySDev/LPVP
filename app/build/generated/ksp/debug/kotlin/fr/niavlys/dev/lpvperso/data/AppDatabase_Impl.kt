package fr.niavlys.dev.lpvperso.`data`

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _stockDao: Lazy<StockDao> = lazy {
    StockDao_Impl(this)
  }

  private val _recipeDao: Lazy<RecipeDao> = lazy {
    RecipeDao_Impl(this)
  }

  private val _orderDao: Lazy<OrderDao> = lazy {
    OrderDao_Impl(this)
  }

  private val _settingsDao: Lazy<SettingsDao> = lazy {
    SettingsDao_Impl(this)
  }

  private val _maintenanceDao: Lazy<MaintenanceDao> = lazy {
    MaintenanceDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "04efd6299d1ca59503ae3e4fa2356949", "70ce979414f0ace27eb6b93a51340eeb") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `stock_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `unit` TEXT NOT NULL, `quantity` REAL NOT NULL, `purchasePrice` REAL, `lowStockThreshold` REAL, `kind` TEXT NOT NULL, `bottleVolumeMl` REAL, `packMultiplier` INTEGER NOT NULL, `unitPrice` REAL, `bottleType` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `recipes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `notes` TEXT NOT NULL, `baseMl` REAL NOT NULL, `nicotineMl` REAL NOT NULL, `aromaStockItemId` INTEGER, `aromaMl` REAL NOT NULL, `bottleStockItemId` INTEGER, `bottleCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `recipe_ingredients` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `recipeId` INTEGER NOT NULL, `stockItemId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, FOREIGN KEY(`recipeId`) REFERENCES `recipes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`stockItemId`) REFERENCES `stock_items`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_recipe_ingredients_recipeId` ON `recipe_ingredients` (`recipeId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_recipe_ingredients_stockItemId` ON `recipe_ingredients` (`stockItemId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderedAt` INTEGER NOT NULL, `receivedAt` INTEGER, `status` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `order_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderId` INTEGER NOT NULL, `stockItemId` INTEGER NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, FOREIGN KEY(`orderId`) REFERENCES `orders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`stockItemId`) REFERENCES `stock_items`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_orderId` ON `order_items` (`orderId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_stockItemId` ON `order_items` (`stockItemId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `price_settings` (`id` INTEGER NOT NULL, `basePricePerMl` REAL NOT NULL, `nicotinePricePerMl` REAL NOT NULL, `bottlePrice` REAL NOT NULL, `defaultFees` REAL NOT NULL, `defaultMarginPercent` REAL NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `app_settings` (`id` INTEGER NOT NULL, `themeMode` TEXT NOT NULL, `diyCalculatorUrl` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '04efd6299d1ca59503ae3e4fa2356949')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `stock_items`")
        connection.execSQL("DROP TABLE IF EXISTS `recipes`")
        connection.execSQL("DROP TABLE IF EXISTS `recipe_ingredients`")
        connection.execSQL("DROP TABLE IF EXISTS `orders`")
        connection.execSQL("DROP TABLE IF EXISTS `order_items`")
        connection.execSQL("DROP TABLE IF EXISTS `price_settings`")
        connection.execSQL("DROP TABLE IF EXISTS `app_settings`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsStockItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStockItems.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("unit", TableInfo.Column("unit", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("quantity", TableInfo.Column("quantity", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("purchasePrice", TableInfo.Column("purchasePrice", "REAL", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("lowStockThreshold", TableInfo.Column("lowStockThreshold", "REAL",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("kind", TableInfo.Column("kind", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("bottleVolumeMl", TableInfo.Column("bottleVolumeMl", "REAL", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("packMultiplier", TableInfo.Column("packMultiplier", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("unitPrice", TableInfo.Column("unitPrice", "REAL", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStockItems.put("bottleType", TableInfo.Column("bottleType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStockItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStockItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoStockItems: TableInfo = TableInfo("stock_items", _columnsStockItems,
            _foreignKeysStockItems, _indicesStockItems)
        val _existingStockItems: TableInfo = read(connection, "stock_items")
        if (!_infoStockItems.equals(_existingStockItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |stock_items(fr.niavlys.dev.lpvperso.data.StockItemEntity).
              | Expected:
              |""".trimMargin() + _infoStockItems + """
              |
              | Found:
              |""".trimMargin() + _existingStockItems)
        }
        val _columnsRecipes: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRecipes.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("notes", TableInfo.Column("notes", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("baseMl", TableInfo.Column("baseMl", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("nicotineMl", TableInfo.Column("nicotineMl", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("aromaStockItemId", TableInfo.Column("aromaStockItemId", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("aromaMl", TableInfo.Column("aromaMl", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("bottleStockItemId", TableInfo.Column("bottleStockItemId", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("bottleCount", TableInfo.Column("bottleCount", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipes.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRecipes: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesRecipes: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoRecipes: TableInfo = TableInfo("recipes", _columnsRecipes, _foreignKeysRecipes,
            _indicesRecipes)
        val _existingRecipes: TableInfo = read(connection, "recipes")
        if (!_infoRecipes.equals(_existingRecipes)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |recipes(fr.niavlys.dev.lpvperso.data.RecipeEntity).
              | Expected:
              |""".trimMargin() + _infoRecipes + """
              |
              | Found:
              |""".trimMargin() + _existingRecipes)
        }
        val _columnsRecipeIngredients: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRecipeIngredients.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipeIngredients.put("recipeId", TableInfo.Column("recipeId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipeIngredients.put("stockItemId", TableInfo.Column("stockItemId", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipeIngredients.put("quantity", TableInfo.Column("quantity", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRecipeIngredients.put("unit", TableInfo.Column("unit", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRecipeIngredients: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysRecipeIngredients.add(TableInfo.ForeignKey("recipes", "CASCADE", "NO ACTION",
            listOf("recipeId"), listOf("id")))
        _foreignKeysRecipeIngredients.add(TableInfo.ForeignKey("stock_items", "CASCADE",
            "NO ACTION", listOf("stockItemId"), listOf("id")))
        val _indicesRecipeIngredients: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesRecipeIngredients.add(TableInfo.Index("index_recipe_ingredients_recipeId", false,
            listOf("recipeId"), listOf("ASC")))
        _indicesRecipeIngredients.add(TableInfo.Index("index_recipe_ingredients_stockItemId", false,
            listOf("stockItemId"), listOf("ASC")))
        val _infoRecipeIngredients: TableInfo = TableInfo("recipe_ingredients",
            _columnsRecipeIngredients, _foreignKeysRecipeIngredients, _indicesRecipeIngredients)
        val _existingRecipeIngredients: TableInfo = read(connection, "recipe_ingredients")
        if (!_infoRecipeIngredients.equals(_existingRecipeIngredients)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |recipe_ingredients(fr.niavlys.dev.lpvperso.data.RecipeIngredientEntity).
              | Expected:
              |""".trimMargin() + _infoRecipeIngredients + """
              |
              | Found:
              |""".trimMargin() + _existingRecipeIngredients)
        }
        val _columnsOrders: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOrders.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("orderedAt", TableInfo.Column("orderedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("receivedAt", TableInfo.Column("receivedAt", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrders.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOrders: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOrders: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoOrders: TableInfo = TableInfo("orders", _columnsOrders, _foreignKeysOrders,
            _indicesOrders)
        val _existingOrders: TableInfo = read(connection, "orders")
        if (!_infoOrders.equals(_existingOrders)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |orders(fr.niavlys.dev.lpvperso.data.OrderEntity).
              | Expected:
              |""".trimMargin() + _infoOrders + """
              |
              | Found:
              |""".trimMargin() + _existingOrders)
        }
        val _columnsOrderItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOrderItems.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrderItems.put("orderId", TableInfo.Column("orderId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrderItems.put("stockItemId", TableInfo.Column("stockItemId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOrderItems.put("quantity", TableInfo.Column("quantity", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOrderItems.put("unit", TableInfo.Column("unit", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOrderItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysOrderItems.add(TableInfo.ForeignKey("orders", "CASCADE", "NO ACTION",
            listOf("orderId"), listOf("id")))
        _foreignKeysOrderItems.add(TableInfo.ForeignKey("stock_items", "CASCADE", "NO ACTION",
            listOf("stockItemId"), listOf("id")))
        val _indicesOrderItems: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesOrderItems.add(TableInfo.Index("index_order_items_orderId", false,
            listOf("orderId"), listOf("ASC")))
        _indicesOrderItems.add(TableInfo.Index("index_order_items_stockItemId", false,
            listOf("stockItemId"), listOf("ASC")))
        val _infoOrderItems: TableInfo = TableInfo("order_items", _columnsOrderItems,
            _foreignKeysOrderItems, _indicesOrderItems)
        val _existingOrderItems: TableInfo = read(connection, "order_items")
        if (!_infoOrderItems.equals(_existingOrderItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |order_items(fr.niavlys.dev.lpvperso.data.OrderItemEntity).
              | Expected:
              |""".trimMargin() + _infoOrderItems + """
              |
              | Found:
              |""".trimMargin() + _existingOrderItems)
        }
        val _columnsPriceSettings: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPriceSettings.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPriceSettings.put("basePricePerMl", TableInfo.Column("basePricePerMl", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPriceSettings.put("nicotinePricePerMl", TableInfo.Column("nicotinePricePerMl",
            "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPriceSettings.put("bottlePrice", TableInfo.Column("bottlePrice", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPriceSettings.put("defaultFees", TableInfo.Column("defaultFees", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPriceSettings.put("defaultMarginPercent", TableInfo.Column("defaultMarginPercent",
            "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPriceSettings: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPriceSettings: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPriceSettings: TableInfo = TableInfo("price_settings", _columnsPriceSettings,
            _foreignKeysPriceSettings, _indicesPriceSettings)
        val _existingPriceSettings: TableInfo = read(connection, "price_settings")
        if (!_infoPriceSettings.equals(_existingPriceSettings)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |price_settings(fr.niavlys.dev.lpvperso.data.PriceSettingsEntity).
              | Expected:
              |""".trimMargin() + _infoPriceSettings + """
              |
              | Found:
              |""".trimMargin() + _existingPriceSettings)
        }
        val _columnsAppSettings: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAppSettings.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppSettings.put("themeMode", TableInfo.Column("themeMode", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAppSettings.put("diyCalculatorUrl", TableInfo.Column("diyCalculatorUrl", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAppSettings: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAppSettings: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoAppSettings: TableInfo = TableInfo("app_settings", _columnsAppSettings,
            _foreignKeysAppSettings, _indicesAppSettings)
        val _existingAppSettings: TableInfo = read(connection, "app_settings")
        if (!_infoAppSettings.equals(_existingAppSettings)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |app_settings(fr.niavlys.dev.lpvperso.data.AppSettingsEntity).
              | Expected:
              |""".trimMargin() + _infoAppSettings + """
              |
              | Found:
              |""".trimMargin() + _existingAppSettings)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "stock_items", "recipes",
        "recipe_ingredients", "orders", "order_items", "price_settings", "app_settings")
  }

  public override fun clearAllTables() {
    super.performClear(true, "stock_items", "recipes", "recipe_ingredients", "orders",
        "order_items", "price_settings", "app_settings")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(StockDao::class, StockDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(RecipeDao::class, RecipeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(OrderDao::class, OrderDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SettingsDao::class, SettingsDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(MaintenanceDao::class, MaintenanceDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun stockDao(): StockDao = _stockDao.value

  public override fun recipeDao(): RecipeDao = _recipeDao.value

  public override fun orderDao(): OrderDao = _orderDao.value

  public override fun settingsDao(): SettingsDao = _settingsDao.value

  public override fun maintenanceDao(): MaintenanceDao = _maintenanceDao.value
}
