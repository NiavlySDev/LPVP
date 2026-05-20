package fr.niavlys.dev.lpvperso.domain

import kotlin.math.max

data class PriceCalculationInput(
    val baseMl: Double,
    val nicotineMl: Double,
    val aromaMl: Double,
    val aromaPrice: Double,
    val aromaBottleMl: Double,
    val bottlePrice: Double,
    val fees: Double,
    val marginPercent: Double,
    val basePricePerMl: Double,
    val nicotinePricePerMl: Double,
)

data class PriceCalculationResult(
    val baseCost: Double,
    val nicotineCost: Double,
    val aromaCost: Double,
    val bottleCost: Double,
    val fees: Double,
    val totalCost: Double,
    val totalMl: Double,
    val costPerMl: Double,
    val salePrice: Double,
    val estimatedProfit: Double,
)

object PriceCalculator {
    fun calculate(input: PriceCalculationInput): PriceCalculationResult {
        val baseCost = input.baseMl * input.basePricePerMl
        val nicotineCost = input.nicotineMl * input.nicotinePricePerMl
        val aromaCost = if (input.aromaBottleMl > 0.0) {
            input.aromaMl * (input.aromaPrice / input.aromaBottleMl)
        } else {
            0.0
        }
        val totalCost = baseCost + nicotineCost + aromaCost + input.bottlePrice + input.fees
        val totalMl = input.baseMl + input.nicotineMl + input.aromaMl
        val costPerMl = if (totalMl > 0.0) totalCost / totalMl else 0.0
        val salePrice = totalCost * (1.0 + max(input.marginPercent, 0.0) / 100.0)
        return PriceCalculationResult(
            baseCost = baseCost,
            nicotineCost = nicotineCost,
            aromaCost = aromaCost,
            bottleCost = input.bottlePrice,
            fees = input.fees,
            totalCost = totalCost,
            totalMl = totalMl,
            costPerMl = costPerMl,
            salePrice = salePrice,
            estimatedProfit = salePrice - totalCost,
        )
    }
}
