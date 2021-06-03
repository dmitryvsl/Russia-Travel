package com.example.russiatravel.repository

import android.util.Log
import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.network.model.User
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.Exception

@ActivityScoped
class UserRepository @Inject constructor(
    private val retrofitService: RetrofitService
) {
    suspend fun authUser(login: String, password: String): DataState<User> {
        val response = try {
            retrofitService.authUser(login, password)
        } catch (e: Exception) {
            return DataState.Error(e.message.toString())
        }
        return if (response.statusId == 200) {
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }

    suspend fun createAccount(name: String, email: String, password: String): DataState<User>{
        val response = try{
            retrofitService.createAccount(name, email, password)
        }catch (e: Exception){
            return DataState.Error(e.message.toString())
        }
        return if (response.statusId == 200) {
            DataState.Success(response.detail)
        }else{
            DataState.Error(response.error)
        }
    }


}