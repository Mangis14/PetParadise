package com.petparadise.game.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petparadise.game.data.GameRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class GameViewModel(private val gameRepository: GameRepository) : ViewModel() {

    private val _petCoins = MutableStateFlow(0L)
    val petCoins: StateFlow<Long> = _petCoins.asStateFlow()

    private val _ownedPets = MutableStateFlow<List<Pet>>(emptyList())
    val ownedPets: StateFlow<List<Pet>> = _ownedPets.asStateFlow()

    private val _petCoinRateBonuses = MutableStateFlow<Map<String, Long>>(emptyMap())
    private val _globalCoinMultiplier = MutableStateFlow(1.0)

    val availablePets = listOf(
        Pet("Puppy", 1),
        Pet("Kitten", 2)
    )

    val availableUpgrades = listOf(
        Upgrade("Enhanced Puppy Playtime", 50, UpgradeEffect.PetCoinRateBonus("Puppy", 1)),
        Upgrade("Super Kitten Naps", 75, UpgradeEffect.PetCoinRateBonus("Kitten", 2)),
        Upgrade("Global Coin Boost", 100, UpgradeEffect.GlobalCoinMultiplier(1.1))
    )

    init {
        loadGameState()
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val generatedCoins = _ownedPets.value.sumOf {
                    val baseRate = it.coinGenerationRate
                    val bonus = _petCoinRateBonuses.value[it.name] ?: 0L
                    baseRate + bonus
                }
                _petCoins.value += (generatedCoins * _globalCoinMultiplier.value).toLong()
                saveGameState()
            }
        }
    }

    private fun loadGameState() {
        val gameState = gameRepository.getGameState()
        if (gameState != null) {
            _petCoins.value = gameState.petCoins
            _globalCoinMultiplier.value = gameState.globalCoinMultiplier

            val ownedPetsFromDb = gameRepository.getOwnedPets()
            _ownedPets.value = ownedPetsFromDb.map { Pet(it.petName, it.coinGenerationRate) }

            val petRateBonusesFromDb = gameRepository.getPetRateBonuses()
            _petCoinRateBonuses.value = petRateBonusesFromDb

            val timeElapsed = (Clock.System.now().toEpochMilliseconds() - gameState.lastSaveTime) / 1000 // in seconds
            val offlineEarnings = timeElapsed * _ownedPets.value.sumOf { pet ->
                val baseRate = pet.coinGenerationRate
                val bonus = _petCoinRateBonuses.value[pet.name] ?: 0L
                baseRate + bonus
            }
            _petCoins.value += (offlineEarnings * _globalCoinMultiplier.value).toLong()
        }
    }

    private fun saveGameState() {
        gameRepository.saveGameState(
            _petCoins.value,
            Clock.System.now().toEpochMilliseconds(),
            _globalCoinMultiplier.value
        )
        gameRepository.saveOwnedPets(_ownedPets.value)
        gameRepository.savePetRateBonuses(_petCoinRateBonuses.value)
    }

    fun onPetTapped() {
        _petCoins.value++
        saveGameState()
    }

    fun purchasePet(pet: Pet) {
        // For simplicity, pet cost is hardcoded to 10 for now
        if (_petCoins.value >= 10) {
            _petCoins.value -= 10
            _ownedPets.value += pet
            saveGameState()
        }
    }

    fun purchaseUpgrade(upgrade: Upgrade) {
        if (_petCoins.value >= upgrade.cost) {
            _petCoins.value -= upgrade.cost
            when (upgrade.effect) {
                is UpgradeEffect.PetCoinRateBonus -> {
                    _petCoinRateBonuses.value = _petCoinRateBonuses.value.toMutableMap().apply {
                        put(upgrade.effect.petName, (get(upgrade.effect.petName) ?: 0L) + upgrade.effect.bonusAmount)
                    }
                }
                is UpgradeEffect.GlobalCoinMultiplier -> {
                    _globalCoinMultiplier.value *= upgrade.effect.multiplier
                }
            }
            saveGameState()
        }
    }
}