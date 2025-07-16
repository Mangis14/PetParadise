package com.petparadise.game.data

import com.petparadise.game.cache.GameState
import com.petparadise.game.feature.Pet
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameRepository(private val localDataSource: LocalDataSource) : KoinComponent {

    fun getGameState(): GameState? {
        return localDataSource.getGameState()
    }

    fun saveGameState(petCoins: Long, lastSaveTime: Long, globalCoinMultiplier: Double) {
        val existingState = localDataSource.getGameState()
        if (existingState != null) {
            localDataSource.updateGameState(existingState.id, petCoins, lastSaveTime, globalCoinMultiplier)
        } else {
            localDataSource.saveGameState(petCoins, lastSaveTime, globalCoinMultiplier)
        }
    }

    fun getOwnedPets(): List<Pet> {
        return localDataSource.getOwnedPets().map { Pet(it.petName, it.coinGenerationRate) }
    }

    fun saveOwnedPets(pets: List<Pet>) {
        localDataSource.saveOwnedPets(pets)
    }

    fun getPetRateBonuses(): Map<String, Long> {
        return localDataSource.getPetRateBonuses().associate { it.petName to it.bonusAmount }
    }

    fun savePetRateBonuses(bonuses: Map<String, Long>) {
        localDataSource.savePetRateBonuses(bonuses)
    }
}