package ru.ok.itmo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _i = MutableStateFlow(0)
    val i = _i.asStateFlow()

    fun run() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (counter in 0..100) {
                    delay(100)
                    _i.value = counter
                }
            }
        }
    }
}