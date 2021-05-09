package com.example.russiatravel.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russiatravel.network.DataState
import com.example.russiatravel.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(){
    var token = mutableStateOf("")
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    fun authUser(login: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            when (val result = repository.authUser(login, password)){
                is DataState.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    token.value = result.data.toString()
                    //TODO Добавить токен в бд
                }
                is DataState.Error ->{
                    loadError.value = result.error!!
                    isLoading.value = false
                }
            }
        }
    }

    fun createAccount(name: String, email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            when (val result = repository.createAccount(name, email, password)){
                is DataState.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    token.value = result.data.toString()
                    //TODO Добавить токен в бд
                }
                is DataState.Error ->{
                    loadError.value = result.error!!
                    isLoading.value = false
                }
            }
        }
    }

}