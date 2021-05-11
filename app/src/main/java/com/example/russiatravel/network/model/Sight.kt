package com.example.russiatravel.network.model

import com.google.gson.annotations.SerializedName
import dagger.multibindings.IntoMap
import java.io.Serializable

data class Sight (
    val id: Int,
    val description: String,
    val title: String,
    val images: List<String>,
)