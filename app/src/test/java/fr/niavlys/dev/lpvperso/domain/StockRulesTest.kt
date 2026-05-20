package fr.niavlys.dev.lpvperso.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StockRulesTest {
    @Test
    fun allowsConsumptionWhenEveryLineHasEnoughStock() {
        val result = StockRules.checkAvailability(
            listOf(
                StockLine(1, "Base", available = 100.0, required = 80.0),
                StockLine(2, "Arome", available = 20.0, required = 10.0),
            ),
        )

        assertTrue(result.canConsume)
        assertTrue(result.missing.isEmpty())
    }

    @Test
    fun blocksConsumptionWhenOneLineIsMissing() {
        val result = StockRules.checkAvailability(
            listOf(
                StockLine(1, "Base", available = 50.0, required = 80.0),
                StockLine(2, "Arome", available = 20.0, required = 10.0),
            ),
        )

        assertFalse(result.canConsume)
        assertTrue(result.missing.single().name == "Base")
    }
}
