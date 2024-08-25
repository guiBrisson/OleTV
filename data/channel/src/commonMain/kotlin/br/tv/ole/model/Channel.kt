package br.tv.ole.model

data class Channel(
    val id: Long,
    val number: String,
    val name: String,
    val genre: String,
    val type: String,
    val classification: String,
    val epgHash: String,
    val hasCatchUp: Boolean,
    val hasStartOver: Boolean,
    val isFavorite: Boolean,
    val watchingTime: Long,
)
