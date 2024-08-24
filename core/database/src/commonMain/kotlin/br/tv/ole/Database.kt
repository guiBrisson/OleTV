package br.tv.ole

import app.cash.sqldelight.db.SqlDriver
import br.tv.ole.db.OleTvDb
import br.tv.ole.service.PlaceholderService
import br.tv.ole.service.placeholderService
import kotlinx.coroutines.CoroutineDispatcher


class Database(
    private val sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher,
) {
    private val scope: DatabaseScope = object : DatabaseScope {
        override val databaseRef: OleTvDb = OleTvDb(sqlDriver)
        override val backgroundDispatcher: CoroutineDispatcher = dispatcher
    }

    val placeholderTable: PlaceholderService = scope.placeholderService()
}

interface DatabaseScope {
    val databaseRef: OleTvDb
    val backgroundDispatcher: CoroutineDispatcher
}
