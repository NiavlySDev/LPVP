package fr.niavlys.dev.lpvperso

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.niavlys.dev.lpvperso.data.AppSettingsEntity
import fr.niavlys.dev.lpvperso.data.OrderStatus
import fr.niavlys.dev.lpvperso.data.PriceSettingsEntity
import fr.niavlys.dev.lpvperso.data.RecipeEntity
import fr.niavlys.dev.lpvperso.data.StockKind
import fr.niavlys.dev.lpvperso.data.StockItemEntity
import fr.niavlys.dev.lpvperso.data.ThemeMode
import fr.niavlys.dev.lpvperso.data.UnitType
import fr.niavlys.dev.lpvperso.domain.PriceCalculationInput
import fr.niavlys.dev.lpvperso.ui.LpvViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: LpvViewModel by viewModels {
        LpvViewModel.factory((application as LPVApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            LpvTheme(state.appSettings.themeMode) {
                LpvApp(state, viewModel)
            }
        }
    }
}

enum class Screen(val label: String) {
    RECIPES("Recettes"),
    STOCK("Stock"),
    ORDERS("Commandes"),
    CALCULATOR("Calculateur"),
    SETTINGS("Reglages"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LpvApp(state: fr.niavlys.dev.lpvperso.ui.UiState, viewModel: LpvViewModel) {
    var screen by remember { mutableStateOf(Screen.RECIPES) }
    val snackbarHost = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let { snackbarHost.showSnackbar(it) }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(screen.label, fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        bottomBar = {
            NavigationBar {
                Screen.entries.forEach {
                    NavigationBarItem(
                        selected = screen == it,
                        onClick = { screen = it },
                        icon = { Icon(iconFor(it), contentDescription = it.label) },
                        label = { Text(it.label) },
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHost) },
    ) { padding ->
        Surface(Modifier.padding(padding).fillMaxSize()) {
            when (screen) {
                Screen.RECIPES -> RecipesScreen(state.stock, state.recipes, viewModel)
                Screen.STOCK -> StockScreen(state.stock, viewModel)
                Screen.ORDERS -> OrdersScreen(state.stock, state.orders, viewModel)
                Screen.CALCULATOR -> CalculatorScreen(state.priceSettings, state.stock, state.recipes, viewModel)
                Screen.SETTINGS -> SettingsScreen(state.appSettings, state.priceSettings, viewModel)
            }
        }
    }
}

fun iconFor(screen: Screen) = when (screen) {
    Screen.RECIPES -> Icons.Default.Restaurant
    Screen.STOCK -> Icons.Default.Inventory2
    Screen.ORDERS -> Icons.AutoMirrored.Filled.ReceiptLong
    Screen.CALCULATOR -> Icons.Default.Calculate
    Screen.SETTINGS -> Icons.Default.Settings
}

@Composable
fun Section(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content,
    )
}

@Composable
fun StockScreen(stock: List<StockItemEntity>, viewModel: LpvViewModel) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Aromes") }
    var quantity by remember { mutableStateOf("") }
    var threshold by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf(UnitType.ML) }
    var kind by remember { mutableStateOf(StockKind.AROME) }
    var bottleVolume by remember { mutableStateOf("10") }
    var packMultiplier by remember { mutableStateOf("1") }
    var unitPrice by remember { mutableStateOf("") }
    var bottleType by remember { mutableStateOf("") }
    Section {
        CardBlock {
            Text("Nouvel article", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(name, { name = it }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(category, { category = it }, label = { Text("Categorie") }, modifier = Modifier.weight(1f))
                UnitPicker(unit, { unit = it }, Modifier.weight(1f))
            }
            KindPicker(kind, {
                kind = it
                unit = if (it == StockKind.FIOLE) UnitType.UNIT else UnitType.ML
                category = kindLabel(it)
                bottleVolume = when (it) {
                    StockKind.BASE -> "1000"
                    StockKind.NICOTINE -> "10"
                    StockKind.AROME -> "10"
                    else -> bottleVolume
                }
                packMultiplier = if (it == StockKind.NICOTINE) "10" else "1"
            })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(quantity, { quantity = it }, label = { Text("Quantite") }, modifier = Modifier.weight(1f))
                OutlinedTextField(threshold, { threshold = it }, label = { Text("Seuil bas") }, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(bottleVolume, { bottleVolume = it }, label = { Text("Contenance ml") }, modifier = Modifier.weight(1f))
                OutlinedTextField(packMultiplier, { packMultiplier = it }, label = { Text("Par commande") }, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(unitPrice, { unitPrice = it }, label = { Text("Prix / bouteille") }, modifier = Modifier.weight(1f))
                OutlinedTextField(bottleType, { bottleType = it }, label = { Text("Type fiole") }, modifier = Modifier.weight(1f))
            }
            Button(onClick = {
                viewModel.addStock(
                    name = name,
                    category = category,
                    unit = unit,
                    quantity = quantity.toDoubleOrNull() ?: 0.0,
                    threshold = threshold.toDoubleOrNull(),
                    kind = kind,
                    bottleVolumeMl = bottleVolume.toDoubleOrNull(),
                    packMultiplier = packMultiplier.toIntOrNull() ?: 1,
                    unitPrice = unitPrice.toDoubleOrNull(),
                    bottleType = bottleType,
                )
                name = ""; quantity = ""; threshold = ""
            }) {
                Icon(Icons.Default.Add, null)
                Text("Ajouter")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(stock) { item ->
                var quickQuantity by remember(item.id) { mutableStateOf("") }
                CardBlock {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(item.name, style = MaterialTheme.typography.titleMedium)
                        Text("${item.category} - ${format(item.quantity)} ${unitLabel(item.unit)}")
                        Text("${kindLabel(item.kind)} - commande: x${item.packMultiplier} ${item.bottleVolumeMl?.let { "${format(it)} ml" } ?: ""}")
                        item.unitPrice?.let { Text("Prix: ${money(it)} / bouteille") }
                        if (item.bottleType.isNotBlank()) Text("Fiole: ${item.bottleType}")
                        if ((item.lowStockThreshold ?: -1.0) >= item.quantity) {
                            Text("Stock bas", color = MaterialTheme.colorScheme.error)
                        }
                        OutlinedTextField(
                            value = quickQuantity,
                            onValueChange = { quickQuantity = it },
                            label = { Text("Quantite rapide (${unitLabel(item.unit)})") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            FilledTonalButton({ viewModel.changeStock(item.id, 1.0) }) { Text("+1") }
                            FilledTonalButton({ viewModel.changeStock(item.id, -1.0) }) { Text("-1") }
                            OutlinedButton({ viewModel.resetStock(item.id) }) { Text("0") }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button({
                                val amount = quickQuantity.toDoubleOrNull() ?: return@Button
                                viewModel.changeStock(item.id, amount)
                                quickQuantity = ""
                            }) { Text("Ajouter") }
                            OutlinedButton({
                                val amount = quickQuantity.toDoubleOrNull() ?: return@OutlinedButton
                                viewModel.setStockQuantity(item.id, amount)
                                quickQuantity = ""
                            }) { Text("Definir") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipesScreen(stock: List<StockItemEntity>, recipes: List<RecipeEntity>, viewModel: LpvViewModel) {
    var name by remember { mutableStateOf("") }
    var baseMl by remember { mutableStateOf("") }
    var nicotineMl by remember { mutableStateOf("") }
    var aromaMl by remember { mutableStateOf("") }
    var bottleCount by remember { mutableStateOf("") }
    var selectedAromaId by remember(stock) { mutableStateOf(stock.firstOrNull { it.kind == StockKind.AROME }?.id) }
    var selectedBottleId by remember(stock) { mutableStateOf(stock.firstOrNull { it.kind == StockKind.FIOLE }?.id) }
    val selectedAroma = stock.firstOrNull { it.id == selectedAromaId }
    val neededAromaBottles = bottleCount(aromaMl.toDoubleOrNull() ?: 0.0, selectedAroma?.bottleVolumeMl)
    Section {
        CardBlock {
            Text("Nouvelle recette", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(name, { name = it }, label = { Text("Nom de la recette") }, modifier = Modifier.fillMaxWidth())
            if (stock.isEmpty()) {
                Text("Ajoute d'abord des articles dans le stock pour pouvoir composer une recette.")
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(baseMl, { baseMl = it }, label = { Text("Base ml") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(nicotineMl, { nicotineMl = it }, label = { Text("Nicotine ml") }, modifier = Modifier.weight(1f))
                }
                StockItemPicker(
                    label = "Arome",
                    items = stock.filter { it.kind == StockKind.AROME },
                    selectedId = selectedAromaId,
                    onSelected = { selectedAromaId = it },
                )
                OutlinedTextField(aromaMl, { aromaMl = it }, label = { Text("Arome ml") }, modifier = Modifier.fillMaxWidth())
                Text("Fioles d'arome necessaires: $neededAromaBottles")
                StockItemPicker(
                    label = "Fiole finale",
                    items = stock.filter { it.kind == StockKind.FIOLE },
                    selectedId = selectedBottleId,
                    onSelected = { selectedBottleId = it },
                )
                OutlinedTextField(bottleCount, { bottleCount = it }, label = { Text("Nombre de fioles finales") }, modifier = Modifier.fillMaxWidth())
            }
            Button(onClick = {
                viewModel.addRecipe(
                    name = name,
                    baseMl = baseMl.toDoubleOrNull() ?: 0.0,
                    nicotineMl = nicotineMl.toDoubleOrNull() ?: 0.0,
                    aromaStockItemId = selectedAromaId,
                    aromaMl = aromaMl.toDoubleOrNull() ?: 0.0,
                    bottleStockItemId = selectedBottleId,
                    bottleCount = bottleCount.toIntOrNull() ?: 0,
                )
                name = ""; baseMl = ""; nicotineMl = ""; aromaMl = ""; bottleCount = ""
            }) {
                Icon(Icons.Default.Add, null)
                Text("Sauvegarder")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(recipes) { recipe ->
                CardBlock {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                            Text("Base ${format(recipe.baseMl)} ml - Nico ${format(recipe.nicotineMl)} ml - Arome ${format(recipe.aromaMl)} ml")
                            if (recipe.bottleCount > 0) Text("${recipe.bottleCount} fiole(s) finale(s)")
                        }
                        Button({ viewModel.completeRecipe(recipe.id) }) {
                            Icon(Icons.Default.Check, null)
                            Text("Faite")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrdersScreen(stock: List<StockItemEntity>, orders: List<fr.niavlys.dev.lpvperso.data.OrderWithItems>, viewModel: LpvViewModel) {
    val quantities = remember(stock) { mutableStateMapOf<Long, String>() }
    var formOpen by remember { mutableStateOf(false) }
    Section {
        CardBlock {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Nouvelle commande LPV", style = MaterialTheme.typography.titleMedium)
                OutlinedButton({ formOpen = !formOpen }) { Text(if (formOpen) "Replier" else "Deplier") }
            }
            if (formOpen) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 320.dp),
                ) {
                    items(stock) { item ->
                        OutlinedTextField(
                            value = quantities[item.id].orEmpty(),
                            onValueChange = { quantities[item.id] = it },
                            label = { Text("${item.name}: nombre de ${orderUnitLabel(item)}") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Button(onClick = {
                    viewModel.createOrder(quantities.map { it.key to (it.value.toDoubleOrNull() ?: 0.0) })
                    quantities.clear()
                    formOpen = false
                }) {
                    Icon(Icons.Default.Add, null)
                    Text("Enregistrer commande")
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(orders) { order ->
                CardBlock {
                    Text("Commande du ${date(order.order.orderedAt)}", style = MaterialTheme.typography.titleMedium)
                    Text(if (order.order.status == OrderStatus.RECUE) "Recue le ${date(order.order.receivedAt)}" else "En attente de reception")
                    Text("${order.items.size} article(s)")
                    if (order.order.status == OrderStatus.COMMANDEE) {
                        Button({ viewModel.receiveOrder(order.order.id) }) { Text("Marquer recue") }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    settings: PriceSettingsEntity,
    stock: List<StockItemEntity>,
    recipes: List<RecipeEntity>,
    viewModel: LpvViewModel,
) {
    val context = LocalContext.current
    var base by remember { mutableStateOf("") }
    var nico by remember { mutableStateOf("") }
    var aroma by remember { mutableStateOf("") }
    var aromaPrice by remember { mutableStateOf("") }
    var aromaBottle by remember { mutableStateOf("10") }
    var selectedBottleId by remember(stock) { mutableStateOf(stock.firstOrNull { it.kind == StockKind.FIOLE }?.id) }
    var bottleCount by remember { mutableStateOf("1") }
    var fees by remember { mutableStateOf(settings.defaultFees.toString()) }
    var selectedRecipeId by remember(recipes) { mutableStateOf<Long?>(null) }
    var result by remember { mutableStateOf("") }
    val selectedBottle = stock.firstOrNull { it.id == selectedBottleId }
    Section(Modifier.verticalScroll(rememberScrollState())) {
        CardBlock {
            Text("Calculateur de prix", style = MaterialTheme.typography.titleMedium)
            RecipePicker(recipes, selectedRecipeId) { id ->
                selectedRecipeId = id
                val recipe = recipes.firstOrNull { it.id == id } ?: return@RecipePicker
                base = recipe.baseMl.takeIf { it > 0.0 }?.toString().orEmpty()
                nico = recipe.nicotineMl.takeIf { it > 0.0 }?.toString().orEmpty()
                aroma = recipe.aromaMl.takeIf { it > 0.0 }?.toString().orEmpty()
                stock.firstOrNull { it.id == recipe.aromaStockItemId }?.let {
                    aromaPrice = it.unitPrice?.toString().orEmpty()
                    aromaBottle = it.bottleVolumeMl?.toString().orEmpty()
                }
                selectedBottleId = recipe.bottleStockItemId ?: selectedBottleId
                bottleCount = recipe.bottleCount.takeIf { it > 0 }?.toString() ?: bottleCount
            }
            listOf(
                "Base ml" to base to { v: String -> base = v },
                "Nicotine ml" to nico to { v: String -> nico = v },
                "Aromes ml" to aroma to { v: String -> aroma = v },
                "Prix / bouteille d'arome" to aromaPrice to { v: String -> aromaPrice = v },
                "Contenance bouteille arôme ml" to aromaBottle to { v: String -> aromaBottle = v },
                "Frais" to fees to { v: String -> fees = v },
            ).forEach { item ->
                val (labelValue, setter) = item
                val (label, value) = labelValue
                OutlinedTextField(value, setter, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
            }
            StockItemPicker(
                label = "Fiole finale",
                items = stock.filter { it.kind == StockKind.FIOLE },
                selectedId = selectedBottleId,
                onSelected = { selectedBottleId = it },
            )
            OutlinedTextField(bottleCount, { bottleCount = it }, label = { Text("Nombre de fioles") }, modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button({
                    val bottleCost = (selectedBottle?.unitPrice ?: 0.0) * (bottleCount.toDoubleOrNull() ?: 0.0)
                    val calculation = viewModel.calculate(
                        PriceCalculationInput(
                            baseMl = base.toDoubleOrNull() ?: 0.0,
                            nicotineMl = nico.toDoubleOrNull() ?: 0.0,
                            aromaMl = aroma.toDoubleOrNull() ?: 0.0,
                            aromaPrice = aromaPrice.toDoubleOrNull() ?: 0.0,
                            aromaBottleMl = aromaBottle.toDoubleOrNull() ?: 0.0,
                            bottlePrice = bottleCost,
                            fees = fees.toDoubleOrNull() ?: 0.0,
                            marginPercent = 0.0,
                            basePricePerMl = settings.basePricePerMl,
                            nicotinePricePerMl = settings.nicotinePricePerMl,
                        ),
                    )
                    result = """
                        Base: ${money(calculation.baseCost)}
                        Nicotine: ${money(calculation.nicotineCost)}
                        Aromes: ${money(calculation.aromaCost)}
                        Fioles: ${money(calculation.bottleCost)}
                        Frais: ${money(calculation.fees)}
                        Total: ${money(calculation.totalCost)}
                        Prix/ml: ${money(calculation.costPerMl)}
                    """.trimIndent()
                }) { Text("Calculer") }
                OutlinedButton({ copy(context, result) }) {
                    Icon(Icons.Default.ContentCopy, null)
                    Text("Copier")
                }
            }
            if (result.isNotBlank()) Text(result)
        }
    }
}

@Composable
fun SettingsScreen(app: AppSettingsEntity, price: PriceSettingsEntity, viewModel: LpvViewModel) {
    val context = LocalContext.current
    var theme by remember(app.themeMode) { mutableStateOf(app.themeMode) }
    var url by remember(app.diyCalculatorUrl) { mutableStateOf(app.diyCalculatorUrl) }
    var base by remember(price.basePricePerMl) { mutableStateOf(price.basePricePerMl.toString()) }
    var nico by remember(price.nicotinePricePerMl) { mutableStateOf(price.nicotinePricePerMl.toString()) }
    var bottle by remember(price.bottlePrice) { mutableStateOf(price.bottlePrice.toString()) }
    var fees by remember(price.defaultFees) { mutableStateOf(price.defaultFees.toString()) }
    var importJson by remember { mutableStateOf("") }
    Section(Modifier.verticalScroll(rememberScrollState())) {
        CardBlock {
            Text("Apparence", style = MaterialTheme.typography.titleMedium)
            ThemePicker(theme, { theme = it })
            Button({ viewModel.saveAppSettings(app.copy(themeMode = theme, diyCalculatorUrl = url)) }) { Text("Sauvegarder theme") }
        }
        CardBlock {
            Text("Calculateur DIY LPV", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(url, { url = it }, label = { Text("Lien global") }, modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button({ viewModel.saveAppSettings(app.copy(themeMode = theme, diyCalculatorUrl = url)) }) { Text("Sauvegarder") }
                OutlinedButton({ context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }) {
                    Icon(Icons.Default.Link, null)
                    Text("Ouvrir")
                }
            }
        }
        CardBlock {
            Text("Prix par defaut", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(base, { base = it }, label = { Text("Base prix/ml") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(nico, { nico = it }, label = { Text("Nicotine prix/ml") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(bottle, { bottle = it }, label = { Text("Fiole") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(fees, { fees = it }, label = { Text("Frais") }, modifier = Modifier.fillMaxWidth())
            Button({
                viewModel.savePriceSettings(
                    price.copy(
                        basePricePerMl = base.toDoubleOrNull() ?: price.basePricePerMl,
                        nicotinePricePerMl = nico.toDoubleOrNull() ?: price.nicotinePricePerMl,
                        bottlePrice = bottle.toDoubleOrNull() ?: 0.0,
                        defaultFees = fees.toDoubleOrNull() ?: 0.0,
                        defaultMarginPercent = 0.0,
                    ),
                )
            }) { Text("Sauvegarder prix") }
        }
        CardBlock {
            Text("Sauvegarde", style = MaterialTheme.typography.titleMedium)
            OutlinedButton({
                viewModel.exportJson { copy(context, it) }
            }) {
                Icon(Icons.Default.ContentCopy, null)
                Text("Exporter JSON")
            }
            OutlinedTextField(
                value = importJson,
                onValueChange = { importJson = it },
                label = { Text("Coller un JSON a importer") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
            )
            Button({
                viewModel.importJson(importJson)
                importJson = ""
            }) {
                Text("Importer JSON")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitPicker(value: UnitType, onChange: (UnitType) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded, { expanded = it }, modifier) {
        OutlinedTextField(
            value = unitLabel(value),
            onValueChange = {},
            readOnly = true,
            label = { Text("Unite") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            UnitType.entries.forEach {
                DropdownMenuItem(text = { Text(unitLabel(it)) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePicker(value: ThemeMode, onChange: (ThemeMode) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        OutlinedTextField(
            value = when (value) {
                ThemeMode.SYSTEM -> "Automatique"
                ThemeMode.LIGHT -> "Clair"
                ThemeMode.DARK -> "Sombre"
            },
            onValueChange = {},
            readOnly = true,
            label = { Text("Theme") },
            leadingIcon = { Icon(Icons.Default.DarkMode, null) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            ThemeMode.entries.forEach {
                DropdownMenuItem(text = { Text(it.name) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KindPicker(value: StockKind, onChange: (StockKind) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        OutlinedTextField(
            value = kindLabel(value),
            onValueChange = {},
            readOnly = true,
            label = { Text("Type de stock") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            StockKind.entries.forEach {
                DropdownMenuItem(text = { Text(kindLabel(it)) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockItemPicker(
    label: String,
    items: List<StockItemEntity>,
    selectedId: Long?,
    onSelected: (Long?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = items.firstOrNull { it.id == selectedId }
    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        OutlinedTextField(
            value = selected?.name ?: "Aucun",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            DropdownMenuItem(text = { Text("Aucun") }, onClick = { onSelected(null); expanded = false })
            items.forEach {
                DropdownMenuItem(text = { Text(it.name) }, onClick = { onSelected(it.id); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePicker(
    recipes: List<RecipeEntity>,
    selectedId: Long?,
    onSelected: (Long) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = recipes.firstOrNull { it.id == selectedId }
    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        OutlinedTextField(
            value = selected?.name ?: "Choisir une recette",
            onValueChange = {},
            readOnly = true,
            label = { Text("Appliquer une recette") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            recipes.forEach {
                DropdownMenuItem(text = { Text(it.name) }, onClick = { onSelected(it.id); expanded = false })
            }
        }
    }
}

@Composable
fun CardBlock(content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
    }
}

fun unitLabel(unit: UnitType) = if (unit == UnitType.ML) "ml" else "unite"
fun kindLabel(kind: StockKind) = when (kind) {
    StockKind.BASE -> "Base"
    StockKind.NICOTINE -> "Nicotine"
    StockKind.AROME -> "Arome"
    StockKind.FIOLE -> "Fiole"
    StockKind.AUTRE -> "Autre"
}
fun orderUnitLabel(item: StockItemEntity): String {
    val pack = item.packMultiplier.coerceAtLeast(1)
    val volume = item.bottleVolumeMl
    return if (item.unit == UnitType.ML && volume != null && volume > 0.0) {
        if (pack > 1) "packs de ${pack}x${format(volume)} ml" else "bouteilles de ${format(volume)} ml"
    } else {
        "unites"
    }
}
fun bottleCount(neededMl: Double, bottleVolumeMl: Double?): Int {
    val volume = bottleVolumeMl ?: return 0
    if (neededMl <= 0.0 || volume <= 0.0) return 0
    return kotlin.math.ceil(neededMl / volume).toInt()
}
fun format(value: Double) = "%,.2f".format(Locale.FRANCE, value).trimEnd('0').trimEnd(',')
fun money(value: Double) = "%.2f EUR".format(Locale.FRANCE, value)
fun date(value: Long?) = value?.let { SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(Date(it)) } ?: "-"
fun copy(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("LPV Perso", text))
}
