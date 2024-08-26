package br.tv.ole.repository

import br.tv.ole.mapper.asModel
import br.tv.ole.mapper.toData
import br.tv.ole.model.ChannelGenre
import br.tv.ole.service.ChannelGenreService

interface ChannelGenreRepository {
    suspend fun selectAllChannelGenres(): List<ChannelGenre>
    suspend fun insertChannelGenre(genres: List<ChannelGenre>)
    suspend fun deleteChannelGenreByOrder(order: Long)
    suspend fun updateChannelGenre(genre: ChannelGenre)
}

fun channelGenreRepository(
    channelGenreService: ChannelGenreService,
) = object : ChannelGenreRepository {
    override suspend fun selectAllChannelGenres(): List<ChannelGenre> =
        channelGenreService.selectAllChannelGenres().map { it.asModel() }

    override suspend fun insertChannelGenre(genres: List<ChannelGenre>) =
        channelGenreService.insertChannelGenre(genres.map { it.toData() })

    override suspend fun deleteChannelGenreByOrder(order: Long) =
        channelGenreService.deleteChannelGenreByOrder(order)

    override suspend fun updateChannelGenre(genre: ChannelGenre) =
        channelGenreService.updateChannelGenre(genre.toData())
}
