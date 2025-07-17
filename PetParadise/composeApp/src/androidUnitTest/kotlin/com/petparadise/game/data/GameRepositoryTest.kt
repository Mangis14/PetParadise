package com.petparadise.game.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.petparadise.game.cache.Database
import com.petparadise.game.feature.Pet
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GameRepositoryTest {
    private lateinit var repository: GameRepository

    @BeforeTest
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val db = Database(driver)
        val localDataSource = LocalDataSource(db)
        repository = GameRepository(localDataSource)
    }

    @Test
    fun saveAndLoadGameState() {
        repository.saveGameState(20, 99L, 2.0)
        val state = repository.getGameState()
        assertNotNull(state)
        assertEquals(20, state!!.petCoins)
        assertEquals(2.0, state.globalCoinMultiplier)
    }

    @Test
    fun saveAndLoadOwnedPets() {
        val pets = listOf(Pet("Dog", 2))
        repository.saveOwnedPets(pets)
        val loaded = repository.getOwnedPets()
        assertEquals(1, loaded.size)
        assertEquals("Dog", loaded[0].name)
    }

    @Test
    fun saveAndLoadPetRateBonuses() {
        val bonuses = mapOf("Dog" to 2L)
        repository.savePetRateBonuses(bonuses)
        val loaded = repository.getPetRateBonuses()
        assertEquals(1, loaded.size)
        assertEquals(2L, loaded["Dog"])
    }
}
