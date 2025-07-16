/*
package com.petparadise.game.data

import com.petparadise.game.cache.Database
import com.petparadise.game.cache.GameState
import com.petparadise.game.cache.OwnedPets
import com.petparadise.game.cache.PetRateBonuses

class LocalDataSource(database: Database) {
    private val gameStateQueries = database.gameStateQueries
    private val ownedPetsQueries = database.ownedPetsQueries
    private val petRateBonusesQueries = database.petRateBonusesQueries

    fun getGameState(): GameState? {
        return gameStateQueries.selectGameState().executeAsOneOrNull()
    }

    fun saveGameState(petCoins: Long, lastSaveTime: Long, globalCoinMultiplier: Double) {
        gameStateQueries.insertGameState(petCoins, lastSaveTime, globalCoinMultiplier)
    }

    fun updateGameState(id: Long, petCoins: Long, lastSaveTime: Long, globalCoinMultiplier: Double) {
        gameStateQueries.updateGameState(petCoins, lastSaveTime, globalCoinMultiplier, id)
    }

    fun getOwnedPets(): List<OwnedPets> {
        return ownedPetsQueries.selectAllOwnedPets().executeAsList()
    }

    fun saveOwnedPets(pets: List<com.petparadise.game.feature.Pet>) {
        ownedPetsQueries.deleteAllOwnedPets()
        pets.forEach { pet ->
            ownedPetsQueries.insertOwnedPet(pet.name, pet.coinGenerationRate)
        }
    }

    fun getPetRateBonuses(): List<PetRateBonuses> {
        return petRateBonusesQueries.selectAllPetRateBonuses().executeAsList()
    }

    fun savePetRateBonuses(bonuses: Map<String, Long>) {
        petRateBonusesQueries.deleteAllPetRateBonuses()
        bonuses.forEach { (petName, bonusAmount) ->
            petRateBonusesQueries.insertPetRateBonus(petName, bonusAmount)
        }
    }
}*/