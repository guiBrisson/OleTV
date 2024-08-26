package br.tv.ole.service

import br.tv.ole.Database
import br.tv.ole.mock.mockMessages
import br.tv.ole.testDbDriver
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TestMessageService {
    private lateinit var service: MessageService

    @BeforeTest
    fun setup() = runTest {
        val database = Database(
            sqlDriver = testDbDriver(),
            log = Logger(StaticConfig()),
            dispatcher = Dispatchers.Default,
        )

        service = database.messageService
        service.insertMessages(mockMessages)
    }

    @Test
    fun `Select All Messages Success`() = runTest {
        val messages = service.selectAllMessages()

        assertEquals(
            expected = mockMessages.size,
            actual = messages.size,
            message = "Could not retrieve all Messages",
        )
    }

    @Test
    fun `Select Message by Id Success`() = runTest {
        val firstMessage = mockMessages.first()

        assertNotNull(
            actual = service.selectMessageById(firstMessage.id),
            message = "Could not retrieve Message by Id",
        )
    }

    @Test
    fun `Update Message Success`() = runTest {
        val updatedMessage = mockMessages.first().copy(
            title = "New Title"
        )
        service.updateMessage(updatedMessage)

        assertEquals(
            expected = updatedMessage,
            actual = service.selectMessageById(updatedMessage.id),
            message = "Could not update Message",
        )
    }

    @Test
    fun `Delete Message by Id Success`() = runTest {
        val firstMessageId = mockMessages.first().id
        service.deleteMessageById(firstMessageId)

        assertNull(
            actual = service.selectMessageById(firstMessageId),
            message = "Could not delete Message by Id",
        )
    }
}
