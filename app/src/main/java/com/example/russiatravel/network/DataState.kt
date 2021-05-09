package com.example.russiatravel.network

sealed class DataState <out T> (
    val data: T? = null,
    val error: String? = null
){

    class Success<T>(data: T): DataState<T>(data = data)
    class Error<T>(error: String): DataState<T>(error = error)

}