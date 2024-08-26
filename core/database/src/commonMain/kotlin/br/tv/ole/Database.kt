package br.tv.ole

import app.cash.sqldelight.db.SqlDriver
import br.tv.ole.db.OleTvDb
import br.tv.ole.service.*
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher

class Database(
    private val sqlDriver: SqlDriver,
    private val log: Logger,
    private val dispatcher: CoroutineDispatcher,
) {
    private val scope: DatabaseScope = object : DatabaseScope {
        override val databaseRef: OleTvDb = OleTvDb(
            driver =  sqlDriver,
        )
        override val logger: Logger = log
        override val backgroundDispatcher: CoroutineDispatcher = dispatcher
    }

    val channelService: ChannelService = scope.channelService()
    val channelGenreService: ChannelGenreService = scope.channelGenreService()
    val messageService: MessageService = scope.messageService()
}

interface DatabaseScope {
    val databaseRef: OleTvDb
    val logger: Logger
    val backgroundDispatcher: CoroutineDispatcher
}
