package com.example.russiatravel.network

import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.network.response.ResponseWrapper
import retrofit2.http.*

interface RetrofitService {
    @Headers("AppKey: konodioda")
    @POST("user/auth.php")
    @FormUrlEncoded
    suspend fun authUser(
        @Field ("login") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<String>


    @Headers("AppKey: konodioda")
    @POST ("user/create_account.php")
    @FormUrlEncoded
    suspend fun createAccount(
        @Field ("name") name: String,
        @Field ("email") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<String>


    @Headers("AppKey: konodioda")
    @GET("locations/regions.php")
    suspend fun fetchRegions(
        @Query ("country_id") countryId:Int = 1
    ) : ResponseWrapper<List<LocationResponse>>

    @Headers("AppKey: konodioda")
    @GET("locations/localities.php")
    suspend fun fetchLocalities(
        @Query ("region_id") regionId:Int,
        @Query ("rest_type") restType: Int
    ) : ResponseWrapper<List<LocationResponse>>


    @Headers("AppKey: konodioda")
    @GET("sight_info/sights.php")
    suspend fun fetchSightList(
        @Query ("locality_id") localityId: Int
    ) : ResponseWrapper<List<Sight>>

    @Headers("AppKey: konodioda")
    @GET("sight_info/sight_detail.php")
    suspend fun getSightDetail(
        @Query ("sight_id") sight_id: Int
    ) : ResponseWrapper<Sight>


}