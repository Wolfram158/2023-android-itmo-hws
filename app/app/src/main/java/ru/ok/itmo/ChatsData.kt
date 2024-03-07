package ru.ok.itmo

data class Message(
    val id: Long,
    val from: String,
    val to: String?,
    val data: Data,
    val time: Long?
)

data class Data(
    val Text: Text?,
    val Image: Image?
)

data class Text(
    val text: String
)

data class Image(
    val link: String?
)