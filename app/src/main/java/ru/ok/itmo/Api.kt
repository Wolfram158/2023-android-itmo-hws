package ru.ok.itmo

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("login")
    suspend fun login(@Body data: AuthRequest): Response<String>

    @POST("logout")
    fun logout(@Header("X-Auth-Token") token: String): Call<Unit>

    @GET("1ch?limit=3000&lastKnownId=5000")
    suspend fun get1ch(): Response<List<Message>>

    @GET("inbox/{user}")
    suspend fun getInbox(@Header("X-Auth-Token") token: String, @Path("user") user: String): Response<List<Message>>

    @POST("messages")
    suspend fun message(@Header("X-Auth-Token") token: String, @Body msg: Message): Response<Int>

    @GET("channels")
    suspend fun getChannels(): Response<List<String>>

    @GET("channel/{name}")
    suspend fun getMessagesFromChannel(@Path("name") name: String): Response<List<Message>>

    @GET("img/{path}")
    suspend fun getImage(@Path("path") path: String): Response<String>

    @GET("users")
    suspend fun getUsers(): Response<List<String>>
}