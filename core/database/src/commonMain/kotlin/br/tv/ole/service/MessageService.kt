package br.tv.ole.service

import br.tv.ole.DatabaseScope
import br.tv.ole.db.Message
import br.tv.ole.transactionWithContext
import kotlinx.coroutines.withContext

interface MessageService {
    suspend fun selectAllMessages(): List<Message>
    suspend fun selectMessageById(id: String): Message?
    suspend fun insertMessages(messages: List<Message>)
    suspend fun updateMessage(message: Message)
    suspend fun deleteMessageById(id: String)
}

fun DatabaseScope.messageService() = object : MessageService {
    private val log = logger.withTag("MessageService")
    private val query = databaseRef.messageQueries

    override suspend fun selectAllMessages(): List<Message> = withContext(backgroundDispatcher) {
        query.selectAllMessages().executeAsList()
    }

    override suspend fun selectMessageById(id: String): Message? = withContext(backgroundDispatcher) {
        query.selectMessageById(id).executeAsOneOrNull()
    }

    override suspend fun insertMessages(messages: List<Message>) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            messages.forEach { message ->
                query.insertMessage(
                    id = message.id,
                    title = message.title,
                    content = message.content,
                    sent = message.sent,
                    read = message.read,
                )
            }
        }.also {
            log.i { "Inserting ${messages.size} Messages into database" }
        }

    override suspend fun updateMessage(message: Message) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.updateMessage(
                id = message.id,
                title = message.title,
                content = message.content,
                sent = message.sent,
                read = message.read,
            )
        }.also {
            log.i { "Message ${message.id} is being updated" }
        }

    override suspend fun deleteMessageById(id: String) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.deleteMessageById(id)
        }.also {
            log.i { "Deleting Message $id from database" }
        }
}
