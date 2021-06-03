package com.example.russiatravel.network

import com.example.russiatravel.network.model.RouteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteApi {

    @GET("/maps/api/directions/json")
    suspend fun getRoute(
        @Query("origin") position: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String = "AIzaSyDPAWGfdsy1KxMUOMc6D8R4WzI1bMBklms"
    ): RouteResponse
}