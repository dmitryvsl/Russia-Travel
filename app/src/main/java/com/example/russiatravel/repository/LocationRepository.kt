package com.example.russiatravel.repository

import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.network.model.LocationResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class LocationRepository @Inject constructor(
    private val retrofitService: RetrofitService
) {

    suspend fun fetchRegion(): DataState<List<LocationResponse>>{
        val response = try {
            retrofitService.fetchRegions()
        } catch (e: Exception){
            return DataState.Error (e.message.toString())
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }


    suspend fun fetchLocalities(regionId: Int, restType: Int): DataState<List<LocationResponse>>{
        val response = try {
            retrofitService.fetchLocalities(regionId, restType)
        } catch (e: Exception){
            return DataState.Error (e.message.toString())
        }
        return if (response.statusId == 200){
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }
}