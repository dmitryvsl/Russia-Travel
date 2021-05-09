package com.example.russiatravel.network

import com.example.russiatravel.network.post_query.AuthBody
import com.example.russiatravel.network.response.ResponseWrapper
import retrofit2.http.*

interface RetrofitService {

    @POST("user/auth.php")
    @FormUrlEncoded
    suspend fun authUser(
        @Field ("login") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<String>

    @POST ("user/create_account.php")
    @FormUrlEncoded
    suspend fun createAccount(
        @Field ("name") name: String,
        @Field ("email") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<String>

}