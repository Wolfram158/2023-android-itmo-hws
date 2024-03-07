package ru.ok.itmo

import retrofit2.Response

data class Token(private var token: Response<String>?) {
    fun save(res: Response<String>) {
        token = res
    }
    fun code() : Int? {
        return token?.code()
    }
    fun body() : String {
        return token?.body().toString()
    }
}
