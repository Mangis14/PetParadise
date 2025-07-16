package com.petparadise.game.feature

data class Upgrade(val name: String, val cost: Long, val effect: UpgradeEffect)

sealed class UpgradeEffect {
    data class PetCoinRateBonus(val petName: String, val bonusAmount: Long) : UpgradeEffect()
    data class GlobalCoinMultiplier(val multiplier: Double) : UpgradeEffect()
}