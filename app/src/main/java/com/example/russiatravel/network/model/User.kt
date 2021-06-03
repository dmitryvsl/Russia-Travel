package com.example.russiatravel.network.model

import androidx.compose.runtime.compositionLocalOf
import com.google.gson.annotations.SerializedName

data class User(
    val token: String,
    val name: String,
    @SerializedName("photo")
    val avatar: String
)