import com.petparadise.game.cache.Database
import com.petparadise.game.data.GameRepository
import com.petparadise.game.data.LocalDataSource
import com.petparadise.game.feature.Pet
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GameRepositoryTest {
    private lateinit var repository: GameRepository

    @BeforeTest
    fun setUp() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val database = Database(driver)
        val localDataSource = LocalDataSource(database)
        repository = GameRepository(localDataSource)
    }

    @Test
    fun saveAndGetGameState() {
        repository.saveGameState(5, 100, 1.0)
        val state = repository.getGameState()
        assertNotNull(state)
        assertEquals(5, state.petCoins)
        assertEquals(100, state.lastSaveTime)
        assertEquals(1.0, state.globalCoinMultiplier)
    }

    @Test
    fun updateGameState() {
        repository.saveGameState(5, 100, 1.0)
        repository.saveGameState(10, 200, 2.0)
        val state = repository.getGameState()
        assertNotNull(state)
        assertEquals(10, state.petCoins)
        assertEquals(200, state.lastSaveTime)
        assertEquals(2.0, state.globalCoinMultiplier)
    }

    @Test
    fun saveAndGetOwnedPets() {
        repository.saveOwnedPets(listOf(Pet("Dog", 1), Pet("Cat", 2)))
        val pets = repository.getOwnedPets()
        assertEquals(2, pets.size)
        assertEquals(Pet("Dog", 1), pets[0])
        assertEquals(Pet("Cat", 2), pets[1])
    }

    @Test
    fun saveAndGetPetRateBonuses() {
        repository.savePetRateBonuses(mapOf("Dog" to 1L, "Cat" to 2L))
        val bonuses = repository.getPetRateBonuses()
        assertEquals(2, bonuses.size)
        assertEquals(1L, bonuses["Dog"])
        assertEquals(2L, bonuses["Cat"])
    }
}
