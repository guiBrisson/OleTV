package br.tv.ole.mapper

import br.tv.ole.model.ChannelGenre

fun br.tv.ole.db.ChannelGenre.asModel(): ChannelGenre {
    return ChannelGenre(order = order, name = name)
}

fun ChannelGenre.toData(): br.tv.ole.db.ChannelGenre {
    return br.tv.ole.db.ChannelGenre(order = order, name = name)
}
