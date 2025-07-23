import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.petparadise.game.cache.Database
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GameRepositoryTest {
    private lateinit var driver: SqlDriver
    private lateinit var database: Database
    private lateinit var localDataSource: LocalDataSource
    private lateinit var repository: GameRepository

    @BeforeTest
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        database = Database(driver)
        localDataSource = LocalDataSource(database)
        repository = GameRepository(localDataSource)
    }

    @Test
    fun saveGameState_inserts_whenNoneExists() {
        repository.saveGameState(10, 1, 1.0)
        val state = repository.getGameState()
        assertNotNull(state)
        assertEquals(10, state.petCoins)
        assertEquals(1.0, state.globalCoinMultiplier)
    }

    @Test
    fun saveGameState_updates_whenStateExists() {
        repository.saveGameState(10, 1, 1.0)
        val firstState = repository.getGameState()
        val firstId = firstState!!.id
        repository.saveGameState(20, 2, 2.0)
        val secondState = repository.getGameState()
        assertNotNull(secondState)
        assertEquals(firstId, secondState.id)
        assertEquals(20, secondState.petCoins)
        assertEquals(2.0, secondState.globalCoinMultiplier)
    }

    @Test
    fun saveOwnedPets_and_getOwnedPets() {
        val pets = listOf(
            com.petparadise.game.feature.Pet("Puppy", 1),
            com.petparadise.game.feature.Pet("Kitten", 2)
        )
        repository.saveOwnedPets(pets)
        assertEquals(pets, repository.getOwnedPets())
    }

    @Test
    fun savePetRateBonuses_and_getPetRateBonuses() {
        val bonuses = mapOf("Puppy" to 1L, "Kitten" to 2L)
        repository.savePetRateBonuses(bonuses)
        assertEquals(bonuses, repository.getPetRateBonuses())
    }
}
