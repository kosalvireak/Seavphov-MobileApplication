package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.ApiState
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.State
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    //    readonly property

    //    for Carousel
    private val _carouselState = MutableLiveData<ApiState<Book>>()
    val carouselState: LiveData<ApiState<Book>> get() = _carouselState;
    //    for Carousel

    //    for NewestAddition
    private val _newestAdditionState = MutableLiveData<ApiState<ArrayList<Book>>>()
    val newestAdditionState: LiveData<ApiState<ArrayList<Book>>> get() = _newestAdditionState;
    //    for NewestAddition

    //    for ThisWeekHighlight
    private val _thisWeekHighlightState = MutableLiveData<ApiState<ArrayList<Book>>>()
    val thisWeekHighlightState: LiveData<ApiState<ArrayList<Book>>> get() = _thisWeekHighlightState;
//    for ThisWeekHighlight

    fun loadCarousel() {
        val apiService = ApiManager.getSeavphovApiService()
        viewModelScope.launch {
            val carouselResponse = apiService.loadBookById("67769f26b853ad8d58317f0d")
            try {
                if (carouselResponse.isSuccess()) {
                    Log.d("Seavphov", "Load Carousel Success")
                    _carouselState.postValue(ApiState(State.success, carouselResponse.data))
                } else {
                    Log.d("Seavphov", "Load Carousel Error")
                    _carouselState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading profile: $ex")
            }

        }
    }

    fun loadNewestAddition() {
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val response = apiService.loadNewestAddition()
            Log.d("Seavphov", "loadNewestAddition: $response")
            try {
                if (response.isSuccess()) {
                    Log.d("Seavphov", "Load NewestAddition Success")
                    _newestAdditionState.postValue(ApiState(State.success, response.data))
                } else {
                    Log.d("Seavphov", "Load NewestAddition Error")
                    _newestAdditionState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading newestAddition: $ex")
            }
        }
    }

    fun loadThisWeekHighlight() {
        val apiService = ApiManager.getSeavphovApiService()
        viewModelScope.launch {
            val response = apiService.loadAllSeavphov()
            Log.d("Seavphov", "Load ThisWeekHighlight Success $response")
            try {
                if (response.isSuccess()) {
                    Log.d("Seavphov", "Load ThisWeekHighlight Success")
                    _thisWeekHighlightState.postValue(ApiState(State.success, response.data))
                } else {
                    Log.d("Seavphov", "Load ThisWeekHighlight Error")
                    _thisWeekHighlightState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading ThisWeekHighlight: $ex")
            }
        }
    }
}