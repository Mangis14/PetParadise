package com.petparadise.game.feature

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.petparadise.game.cache.Database
import com.petparadise.game.data.GameRepository
import com.petparadise.game.data.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GameViewModelTest {
    private lateinit var viewModel: TestGameViewModel

    private class TestGameViewModel(repo: GameRepository) : GameViewModel(repo) {
        public override fun onCleared() { super.onCleared() }
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val db = Database(driver)
        val repo = GameRepository(LocalDataSource(db))
        viewModel = TestGameViewModel(repo)
    }

    @AfterTest
    fun tearDown() {
        viewModel.onCleared()
        Dispatchers.resetMain()
    }

    @Test
    fun tappingPetIncrementsCoins() {
        viewModel.onPetTapped()
        assertEquals(1L, viewModel.petCoins.value)
    }
}
