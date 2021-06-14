package com.example.russiatravel.viewModel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.model.Feedback
import com.example.russiatravel.network.model.LocationResponse
import com.example.russiatravel.network.model.RouteResponse
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.repository.SightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class FeedbackStatus {
    ERROR, SUCCESS
}

@HiltViewModel
class SightViewModel @Inject constructor(
    private val sightRepository: SightRepository
) : ViewModel() {

    val sights: LiveData<List<Sight>> get() = _sights
    private var _sights: MutableLiveData<List<Sight>> = MutableLiveData()

    val sight: LiveData<Sight> get() = _sight
    private var _sight: MutableLiveData<Sight> = MutableLiveData()

    val feedback: LiveData<Feedback> get() = _feedback
    private var _feedback: MutableLiveData<Feedback> = MutableLiveData()

    val route: LiveData<RouteResponse> get() = _route
    private var _route: MutableLiveData<RouteResponse> = MutableLiveData()

    val addFeedbackState: LiveData<FeedbackStatus> get() = _addFeedbackState
    private var _addFeedbackState: MutableLiveData<FeedbackStatus> = MutableLiveData()

    var checkInBookmark = mutableStateOf(false)
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
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

    fun fetchSights(latitude: Float, longitude: Float) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = sightRepository.fetchNearSights(latitude, longitude)) {
                is DataState.Success -> {
                    isLoading.value = false
                    _sights.postValue(result.data)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = result.error!!
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

    fun getRoute(origin: String, destination: String) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = sightRepository.getRoute(origin, destination)) {
                is DataState.Success -> {
                    Log.d("ViewModel", "Success")
                    isLoading.value = false
                    _route.postValue(result.data)
                }
                is DataState.Error -> {
                    Log.d("ViewModel", "Error")
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

    fun getFeedbacks(sightId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = sightRepository.getFeedbacks(sightId)) {
                is DataState.Success -> {
                    isLoading.value = false
                    _feedback.postValue(result.data)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = result.error!!
                }
            }
        }
    }

    fun removeSights() {
        _sights.postValue(null)
    }

    fun addFeedback(sightId: Int, rating: Int, token: String, feedback: String) {
        viewModelScope.launch {
            isLoading.value = true
            when (sightRepository.addFeedback(sightId, rating, feedback, token)) {
                is DataState.Success -> {
                    isLoading.value = false
                    _addFeedbackState.postValue(FeedbackStatus.SUCCESS)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    _addFeedbackState.postValue(FeedbackStatus.ERROR)
                }
            }
        }
    }

    fun addOrRemoveBookmark(sightId: Int) {
        viewModelScope.launch {
            sightRepository.addOrRemoveBookmark(sightId)
        }
    }

    fun getBookmarks(){
        viewModelScope.launch {
            isLoading.value = true
            when (val response = sightRepository.getBookmarks()) {
                is DataState.Success -> {
                    isLoading.value = false
                    _sights.postValue(response.data!!)
                }
                is DataState.Error -> {
                    isLoading.value = false
                    loadError.value = response.error!!
                }
            }
        }
    }

    fun checkInBookmark (sightId: Int){
        viewModelScope.launch {
            when (val response = sightRepository.checkInBookmark(sightId)){
                is DataState.Success -> {
                    checkInBookmark.value = response.data!!
                }
                is DataState.Error -> {
                    loadError.value = response.error!!
                }
            }
        }
    }
}