package ru.ok.itmo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ChannelRepository

    init {
        val dao = ChannelDB.invoke(application)?.getDao()
        repository = dao?.let { ChannelRepository(it) }!!
    }

    fun addChannel(channel: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addChannel(channel)
        }
    }
}