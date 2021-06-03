package com.example.russiatravel.network.model

data class Sight (
    val id: Int,
    val description: String,
    val title: String,
    val images: List<String>,
    val longitude: String,
    val latitude: Float,
    val location: String,
    val distance: Float? = null
)