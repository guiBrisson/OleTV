package br.tv.ole.service

import br.tv.ole.Database
import br.tv.ole.mock.mockChannelGenres
import br.tv.ole.testDbDriver
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TestChannelGenreService {
    private lateinit var service: ChannelGenreService

    @BeforeTest
    fun setup() = runTest {
        val database = Database(
            sqlDriver = testDbDriver(),
            log = Logger(StaticConfig()),
            dispatcher = Dispatchers.Default,
        )

        service = database.channelGenreService
        service.insertChannelGenre(mockChannelGenres)
    }

    @Test
    fun `Select All Channel Genres Success`() = runTest {
        val genres = service.selectAllChannelGenres()

        assertEquals(
            expected = mockChannelGenres.size,
            actual = genres.size,
            message = "Could not retrieve all Channel Genres"
        )
    }

    @Test
    fun `Delete Channel Genre by Order Success`() = runTest {
        val firstGenreOrder = mockChannelGenres.first().order
        service.deleteChannelGenreByOrder(firstGenreOrder)

        assertNull(
            actual = service.selectAllChannelGenres().firstOrNull { it.order == firstGenreOrder },
            message = "Could not delete Channel Genre by Order",
        )
    }

    @Test
    fun `Update Channel Genre Success`() = runTest {
        val updatedGenre = mockChannelGenres.first().copy(
            name = "New Name"
        )
        service.updateChannelGenre(updatedGenre)

        assertEquals(
            expected = updatedGenre,
            actual = service.selectAllChannelGenres().firstOrNull { it.order == updatedGenre.order },
            message = "Could not update Channel Genre",
        )
    }
}
