package ru.ok.itmo

class ChannelRepository(private val dao: Dao) {
    suspend fun addChannel(channel: String) {
        dao.insertChannels(channel)
    }
}