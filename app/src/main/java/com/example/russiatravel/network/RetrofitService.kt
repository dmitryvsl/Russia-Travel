package com.example.russiatravel.network

import com.example.russiatravel.network.model.Feedback
import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.network.model.User
import com.example.russiatravel.network.response.ResponseWrapper
import com.google.common.math.DoubleMath
import retrofit2.http.*

interface RetrofitService {
    @Headers("AppKey: konodioda")
    @POST("user/auth.php")
    @FormUrlEncoded
    suspend fun authUser(
        @Field ("login") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<User>

    @Headers("AppKey: konodioda")
    @POST ("user/create_account.php")
    @FormUrlEncoded
    suspend fun createAccount(
        @Field ("name") name: String,
        @Field ("email") login: String,
        @Field ("pass") password: String
    ) : ResponseWrapper<User>

    @Headers("AppKey: konodioda")
    @POST("sight_info/add_feedback.php")
    @FormUrlEncoded
    suspend fun addFeedback(
        @Field ("sight") sight: Int,
        @Field ("token") token: String,
        @Field ("feedback") feedback: String,
        @Field ("rating") rating: Int
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

    @Headers("AppKey: konodioda")
    @GET("sight_info/feedback.php")
    suspend fun getFeedbacks(
        @Query ("sight_id") sight_id: Int
    ) : ResponseWrapper<Feedback>



    @Headers("AppKey: konodioda")
    @GET("sight_info/near_sights.php")
    suspend fun getNearSights(
        @Query("longitude") longitude: Float,
        @Query("latitude") latitude: Float
    ) : ResponseWrapper<List<Sight>>




}