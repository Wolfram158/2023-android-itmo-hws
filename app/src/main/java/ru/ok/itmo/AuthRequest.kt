package ru.ok.itmo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("pwd")
    val pwd: String
): Serializable
