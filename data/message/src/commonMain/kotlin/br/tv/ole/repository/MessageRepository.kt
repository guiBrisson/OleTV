package br.tv.ole.repository

import br.tv.ole.mapper.asModel
import br.tv.ole.mapper.toData
import br.tv.ole.model.Message
import br.tv.ole.service.MessageService

interface MessageRepository {
    suspend fun selectAllMessages(): List<Message>
    suspend fun selectMessageById(id: String): Message?
    suspend fun insertMessages(messages: List<Message>)
    suspend fun updateMessage(message: Message)
    suspend fun deleteMessageById(id: String)
}

fun messageRepository(
    messageService: MessageService,
): MessageRepository = object : MessageRepository {
    override suspend fun selectAllMessages(): List<Message> =
        messageService.selectAllMessages().map { it.asModel() }

    override suspend fun selectMessageById(id: String): Message? =
        messageService.selectMessageById(id)?.asModel()

    override suspend fun insertMessages(messages: List<Message>) =
        messageService.insertMessages(messages.map { it.toData() })

    override suspend fun updateMessage(message: Message) =
        messageService.updateMessage(message.toData())

    override suspend fun deleteMessageById(id: String) =
        messageService.deleteMessageById(id)
}
