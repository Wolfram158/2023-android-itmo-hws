package ru.ok.itmo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NumberViewModel: ViewModel() {
    private val _number = MutableLiveData<Int>()
    val number: LiveData<Int> get() = _number

    init {
        _number.value = (0..100).random()
    }
}