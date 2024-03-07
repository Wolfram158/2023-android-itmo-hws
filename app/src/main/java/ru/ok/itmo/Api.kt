package ru.ok.itmo

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("login")
    suspend fun login(@Body data: AuthRequest): Response<String>

    @POST("logout")
    fun logout(@Header("X-Auth-Token") token: String): Call<Unit>
}