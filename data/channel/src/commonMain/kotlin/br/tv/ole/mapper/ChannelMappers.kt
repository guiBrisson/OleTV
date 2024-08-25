package br.tv.ole.mapper

import br.tv.ole.model.Channel

fun br.tv.ole.db.Channel.asModel(): Channel {
    return Channel(
        id = id,
        number = number,
        name = name,
        genre = genre,
        type = type,
        classification = classification,
        epgHash = epgHash,
        hasCatchUp = hasCatchUp,
        hasStartOver = hasStartOver,
        isFavorite = isFavorite,
        watchingTime = watchingTime,
    )
}

fun Channel.toData(): br.tv.ole.db.Channel {
    return br.tv.ole.db.Channel(
        id = id,
        number = number,
        name = name,
        genre = genre,
        type = type,
        classification = classification,
        epgHash = epgHash,
        hasCatchUp = hasCatchUp,
        hasStartOver = hasStartOver,
        isFavorite = isFavorite,
        watchingTime = watchingTime,
    )
}
