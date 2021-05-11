package com.example.russiatravel.repository

import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.network.model.Sight
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class SightRepository @Inject constructor(
    private val retrofitService: RetrofitService
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
}