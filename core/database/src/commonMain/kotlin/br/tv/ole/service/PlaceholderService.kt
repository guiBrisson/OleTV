package br.tv.ole.service

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import br.tv.ole.DatabaseScope
import br.tv.ole.db.Placeholder
import br.tv.ole.transactionWithContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


interface PlaceholderService {
    fun selectAllPlaceholders(): Flow<List<Placeholder>>
    suspend fun insertPlaceholders(placeholder: List<String>)
    fun selectById(id: Long): Flow<List<Placeholder>>
    suspend fun deleteAll()
}

fun DatabaseScope.placeholderService() = object : PlaceholderService {
    override fun selectAllPlaceholders(): Flow<List<Placeholder>> = databaseRef.placeholderQueries
        .selectAll()
        .asFlow()
        .mapToList(Dispatchers.Default)
        .flowOn(backgroundDispatcher)

    override suspend fun insertPlaceholders(placeholder: List<String>) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            placeholder.forEach { placeholder ->
                databaseRef.placeholderQueries.insertPlaceholder(placeholder)
            }
        }

    override fun selectById(id: Long): Flow<List<Placeholder>> = databaseRef.placeholderQueries
        .selectById(id)
        .asFlow()
        .mapToList(Dispatchers.Default)
        .flowOn(backgroundDispatcher)

    override suspend fun deleteAll() =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            databaseRef.placeholderQueries.deleteAll()
        }

}
