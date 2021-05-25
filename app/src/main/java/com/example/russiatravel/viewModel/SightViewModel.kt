package com.example.russiatravel.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.repository.SightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SightViewModel @Inject constructor(
    private val sightRepository: SightRepository
) : ViewModel() {

    val sights: LiveData<List<Sight>> get() = _sights
    private var _sights: MutableLiveData<List<Sight>> = MutableLiveData()

    val sight: LiveData<Sight> get() = _sight
    private var _sight: MutableLiveData<Sight> = MutableLiveData()

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun fetchSights(localityId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = sightRepository.fetchSights(localityId)) {
                is DataState.Success -> {
                    isLoading.value = false
                    _sights.postValue(result.data)
                }
                is DataState.Error -> {
                    Log.d("SightViewModel", result.error!!)
                    isLoading.value = false
                    loadError.value = result.error
                }
            }
        }
    }

    fun getSightDetail(sightId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = sightRepository.getSight(sightId)) {
                is DataState.Success -> {
                    isLoading.value = false
                    _sight.postValue(result.data)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

}