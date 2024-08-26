package br.tv.ole.model

data class Message(
    val id: String,
    val title: String,
    val content: String,
    val sent: Long,
    val read: Long,
)
