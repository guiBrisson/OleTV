package br.tv.ole.service

import br.tv.ole.DatabaseScope
import br.tv.ole.db.Channel
import br.tv.ole.transactionWithContext
import kotlinx.coroutines.withContext

interface ChannelService {
    suspend fun selectAllChannels(): List<Channel>
    suspend fun selectLastWatchedChannels(): List<Channel>
    suspend fun selectById(id: Long): Channel?
    suspend fun insertChannels(channels: List<Channel>)
    suspend fun updateChannel(channel: Channel)
    suspend fun deleteChannelById(id: Long)
}

fun DatabaseScope.channelService() = object : ChannelService {
    private val log = logger.withTag("ChannelService")
    private val query = databaseRef.channelQueries

    override suspend fun selectAllChannels(): List<Channel> = withContext(backgroundDispatcher) {
        query.selectAllChannels().executeAsList()
    }

    override suspend fun selectLastWatchedChannels(): List<Channel> = withContext(backgroundDispatcher) {
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
        }.also {
            log.i { "Inserting ${channels.size} Channels into database" }
        }

    override suspend fun updateChannel(channel: Channel) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.updateChannel(
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
        }.also {
            log.i { "Channel ${channel.id} is being updated" }
        }

    override suspend fun deleteChannelById(id: Long) =
        databaseRef.transactionWithContext(backgroundDispatcher) {
            query.deleteChannelById(id)
        }.also {
            log.i { "Deleting Channel $id from database" }
        }
}
