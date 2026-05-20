package fr.niavlys.dev.lpvperso.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class PriceCalculatorTest {
    @Test
    fun calculatesDetailedCostAndSalePrice() {
        val result = PriceCalculator.calculate(
            PriceCalculationInput(
                baseMl = 100.0,
                nicotineMl = 10.0,
                aromaMl = 15.0,
                aromaPrice = 4.0,
                aromaBottleMl = 10.0,
                bottlePrice = 0.5,
                fees = 1.0,
                marginPercent = 25.0,
                basePricePerMl = 0.01,
                nicotinePricePerMl = 0.08,
            ),
        )

        assertEquals(1.0, result.baseCost, 0.001)
        assertEquals(0.8, result.nicotineCost, 0.001)
        assertEquals(6.0, result.aromaCost, 0.001)
        assertEquals(9.3, result.totalCost, 0.001)
        assertEquals(11.625, result.salePrice, 0.001)
        assertEquals(2.325, result.estimatedProfit, 0.001)
    }
}
