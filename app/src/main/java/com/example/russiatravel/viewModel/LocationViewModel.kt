package com.example.russiatravel.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private var locationRepository: LocationRepository
) : ViewModel() {

    val regions: LiveData<List<LocationResponse>> get() = _regions
    private var _regions: MutableLiveData<List<LocationResponse>> = MutableLiveData()


    val localities: LiveData<List<LocationResponse>> get() = _localities
    private var _localities: MutableLiveData<List<LocationResponse>> = MutableLiveData()

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun fetchRegions(){
        viewModelScope.launch {
            isLoading.value = true
            when (val result = locationRepository.fetchRegion()){
                is DataState.Success -> {
                    Log.d("state", "success")
                    isLoading.value = false
                    _regions.postValue(result.data!!)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

    fun fetchLocalities(regionId: Int, restType: Int){
        viewModelScope.launch {
            isLoading.value = true
            when (val result = locationRepository.fetchLocalities(regionId, restType)){
                is DataState.Success -> {
                    isLoading.value = false
                    _localities.postValue(result.data!!)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

    fun clearLocalities(){
        _localities.postValue(null)
    }
}