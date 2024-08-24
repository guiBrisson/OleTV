package br.tv.ole.service

import br.tv.ole.DatabaseScope
import br.tv.ole.db.Channel
import br.tv.ole.transactionWithContext
import kotlinx.coroutines.withContext

interface ChannelService {
    suspend fun selectAllChannels(): List<Channel>
    suspend fun getLastWatchedChannels(): List<Channel>
    suspend fun selectById(id: Long): Channel?
    suspend fun insertChannels(channels: List<Channel>)
    suspend fun deleteChannelById(id: Long)
}

fun DatabaseScope.channelService() = object : ChannelService {
    private val query = databaseRef.channelQueries

    override suspend fun selectAllChannels(): List<Channel> = withContext(backgroundDispatcher) {
        query.selectAllChannels().executeAsList()
    }

    override suspend fun getLastWatchedChannels(): List<Channel> = withContext(backgroundDispatcher) {
        query.getLastWatchedChannels().executeAsList()
    }

    override suspend fun selectById(id: Long): Channel? = withContext(backgroundDispatcher) {
        query.selectChannelById(id).executeAsOneOrNull()
    }

    override suspend fun insertChannels(channels: List<Channel>) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            channels.forEach { channel ->
                query.insertChannel(
                    id = channel.id,
                    number = channel.number,
                    name = channel.name,
                    genre = channel.genre,
                    type = channel.type,
                    classification = channel.classification,
                    epgHash = channel.epgHash,
                    hasCatchUp = channel.hasCatchUp,
                    hasStartOver = channel.hasStartOver,
                    isFavorite = channel.isFavorite,
                    watchingTime = channel.watchingTime,
                )
            }
        }

    override suspend fun deleteChannelById(id: Long) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.deleteChannelById(id)
        }
}
