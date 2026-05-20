package fr.niavlys.dev.lpvperso.domain

data class StockLine(
    val stockItemId: Long,
    val name: String,
    val available: Double,
    val required: Double,
)

data class StockCheckResult(
    val canConsume: Boolean,
    val missing: List<StockLine>,
)

object StockRules {
    fun checkAvailability(lines: List<StockLine>): StockCheckResult {
        val missing = lines.filter { it.available + 0.0001 < it.required }
        return StockCheckResult(canConsume = missing.isEmpty(), missing = missing)
    }
}
