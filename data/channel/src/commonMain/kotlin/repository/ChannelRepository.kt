package repository

import br.tv.ole.service.ChannelService
import mapper.asModel
import mapper.toData
import model.Channel

interface ChannelRepository {
    suspend fun getAllChannels(): List<Channel>
    suspend fun getLastWatchedChannels(): List<Channel>
    suspend fun insertChannel(channel: List<Channel>)
    suspend fun deleteChannelById(id: Long)
}

fun channelRepository(
    channelService: ChannelService,
) = object : ChannelRepository {
    override suspend fun getAllChannels(): List<Channel> =
        channelService.selectAllChannels().map { it.asModel() }

    override suspend fun getLastWatchedChannels(): List<Channel> =
        channelService.selectLastWatchedChannels().map { it.asModel() }

    override suspend fun insertChannel(channel: List<Channel>) =
        channelService.insertChannels(channel.map { it.toData() })

    override suspend fun deleteChannelById(id: Long) =
        channelService.deleteChannelById(id)
}
