package br.tv.ole.service

import br.tv.ole.DatabaseScope
import br.tv.ole.db.ChannelGenre
import br.tv.ole.transactionWithContext
import kotlinx.coroutines.withContext

interface ChannelGenreService {
    suspend fun selectAllChannelGenres(): List<ChannelGenre>
    suspend fun insertChannelGenre(genres: List<ChannelGenre>)
    suspend fun deleteChannelGenreByOrder(order: Long)
    suspend fun updateChannelGenre(genre: ChannelGenre)
}

fun DatabaseScope.channelGenreService() = object : ChannelGenreService {
    private val log = logger.withTag("ChannelGenreService")
    private val query = databaseRef.channelGenreQueries

    override suspend fun selectAllChannelGenres(): List<ChannelGenre> = withContext(backgroundDispatcher) {
        query.selectAllChannelGenre().executeAsList()
    }

    override suspend fun insertChannelGenre(genres: List<ChannelGenre>) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            genres.forEach { genre ->
                query.insertChannelGenre(order = genre.order, name = genre.name)
            }
        }.also {
            log.i { "Inserting ${genres.size} Channel Genre into database" }
        }

    override suspend fun deleteChannelGenreByOrder(order: Long) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.deleteChannelGenreByOrder(order)
        }.also {
            log.i { "Deleting Channel Genre with order $order from database" }
        }

    override suspend fun updateChannelGenre(genre: ChannelGenre) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.updateChannelGenre(name = genre.name, order = genre.order)
        }.also {
            log.i { "Channel order ${genre.name} is being updated" }
        }
}
