package ru.ok.itmo

class Fragments {
    companion object {
        var a = FragmentSample.newInstance()
        var b = FragmentSample.newInstance()
        var c = FragmentSample.newInstance()
        var d = FragmentSample.newInstance()
        var e = FragmentSample.newInstance()
        var last = FragmentSample.newInstance()
        var fragments = mutableListOf<String>()
        var count = 0
    }
}