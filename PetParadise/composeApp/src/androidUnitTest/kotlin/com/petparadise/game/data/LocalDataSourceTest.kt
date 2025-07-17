package com.petparadise.game.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.petparadise.game.cache.Database
import com.petparadise.game.feature.Pet
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LocalDataSourceTest {
    private lateinit var localDataSource: LocalDataSource

    @BeforeTest
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val db = Database(driver)
        localDataSource = LocalDataSource(db)
    }

    @Test
    fun saveAndLoadGameState() {
        localDataSource.saveGameState(50, 123L, 1.5)
        val state = localDataSource.getGameState()
        assertNotNull(state)
        assertEquals(50, state!!.petCoins)
        assertEquals(1.5, state.globalCoinMultiplier)
    }

    @Test
    fun saveAndLoadOwnedPets() {
        val pets = listOf(Pet("Dog", 2), Pet("Cat", 3))
        localDataSource.saveOwnedPets(pets)
        val fromDb = localDataSource.getOwnedPets()
        assertEquals(2, fromDb.size)
        assertEquals("Dog", fromDb[0].petName)
    }

    @Test
    fun saveAndLoadPetRateBonuses() {
        val bonuses = mapOf("Dog" to 2L, "Cat" to 3L)
        localDataSource.savePetRateBonuses(bonuses)
        val fromDb = localDataSource.getPetRateBonuses()
        assertEquals(2, fromDb.size)
        assertEquals(2L, fromDb.first { it.petName == "Dog" }.bonusAmount)
    }
}
