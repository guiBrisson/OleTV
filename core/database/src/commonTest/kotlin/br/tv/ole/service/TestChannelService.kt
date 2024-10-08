package br.tv.ole.service

import br.tv.ole.Database
import br.tv.ole.mock.mockChannels
import br.tv.ole.testDbDriver
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TestChannelService {
    private lateinit var service: ChannelService

    @BeforeTest
    fun setup() = runTest {
        val database = Database(
            sqlDriver = testDbDriver(),
            log = Logger(StaticConfig()),
            dispatcher = Dispatchers.Default,
        )

        service = database.channelService
        service.insertChannels(mockChannels)
    }

    @Test
    fun `Select All Channels Success`() = runTest {
        val channels = service.selectAllChannels()

        assertEquals(
            expected = mockChannels.size,
            actual = channels.size,
            message = "Could not retrieve all Channels",
        )
    }

    @Test
    fun `Select Last Watched Channels Success`() = runTest {
        val expected = mockChannels
            .filter { it.classification != "ADULT" }
            .filter { it.watchingTime > 0 }
            .sortedByDescending { it.watchingTime }
            .take(10)

        assertEquals(
            expected = expected,
            actual = service.selectLastWatchedChannels(),
            message = "Last Watched Channels does not match"
        )
    }

    @Test
    fun `Select Channel by Id Success`() = runTest {
        val channels = service.selectAllChannels()
        val firstChannel = channels.first()

        assertNotNull(
            actual = service.selectById(firstChannel.id),
            message = "Could not retrieve Channel by Id"
        )
    }

    @Test
    fun `Update Channel Success`() = runTest {
        val updatedChannel = mockChannels.first().copy(
            name = "New Name"
        )
        service.updateChannel(updatedChannel)

        assertEquals(
            expected = updatedChannel,
            actual = service.selectById(updatedChannel.id),
            message = "Could not update Channel",
        )
    }

    @Test
    fun `Delete Channel by Id Success`() = runTest {
        val firstChannelId = mockChannels.first().id
        service.deleteChannelById(firstChannelId)

        assertNull(
            actual = service.selectAllChannels().firstOrNull { it.id == firstChannelId},
            message = "Could not delete Channel by Id"
        )
    }
}
