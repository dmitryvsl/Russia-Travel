package com.example.russiatravel.repository

import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.network.RouteApi
import com.example.russiatravel.network.model.Feedback
import com.example.russiatravel.network.model.RouteResponse
import com.example.russiatravel.network.model.Sight
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.Exception

@ActivityScoped
class SightRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val routeApi: RouteApi
) {

    suspend fun fetchSights(localityId: Int): DataState<List<Sight>> {
        val response = try {
            retrofitService.fetchSightList(localityId)
        } catch (e: Exception) {
            return DataState.Error(e.message.toString())
        }
        return if (response.statusId == 200) {
            DataState.Success(response.detail)
        } else {
            DataState.Error(response.error)
        }
    }

    suspend fun fetchNearSights(latitude: Float, longitude: Float): DataState<List<Sight>> {
        val response = try {
            retrofitService.getNearSights(longitude = longitude, latitude = latitude)
        } catch (e: Exception) {
            return DataState.Error(e.message.toString())
        }
        return if (response.statusId == 200) {
            DataState.Success(response.detail)
        } else {
            DataState.Error(response.error)
        }
    }

    suspend fun getSight(sightId: Int): DataState<Sight> {
        val response = try {
            retrofitService.getSightDetail(sightId)
        } catch (e: Exception) {
            return DataState.Error(e.message.toString())
        }
        return if (response.statusId == 200) {
            DataState.Success(response.detail)
        } else {
            DataState.Error(response.error)
        }
    }

    suspend fun getRoute(origin: String, destination: String) : DataState<RouteResponse>{
        val response = try {
            routeApi.getRoute(origin, destination)
        } catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.status == "OK"){
            DataState.Success(response)
        }else {
            DataState.Error("Ошибка")
        }
    }

    suspend fun getFeedbacks(sightId: Int) : DataState<Feedback>{
        val response = try {
            retrofitService.getFeedbacks(sightId)
        } catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.status == "OK"){
            DataState.Success(response.detail)
        }else {
            DataState.Error(response.error)
        }
    }

    suspend fun addFeedback(sightId: Int, rating: Int, feedback: String, token: String) : DataState<String>{
        val response = try {
            retrofitService.addFeedback(sightId, token, feedback, rating)
        } catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else {
            DataState.Error(response.error)
        }
    }

    suspend fun addOrRemoveBookmark (sightId: Int) : DataState<String>{
        val response = try {
            retrofitService.addOrRemoveBookmark(sightId)
        }catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }

    suspend fun getBookmarks(): DataState<List<Sight>>{
        val response = try {
            retrofitService.getBookmarks()
        }catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }
    suspend fun checkInBookmark(sightId: Int): DataState<Boolean>{
        val response = try {
            retrofitService.checkInBookmark(sightId)
        }catch (e: Exception){
            return DataState.Error(e.message!!)
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }


}