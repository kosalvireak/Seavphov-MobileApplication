package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.Carousel
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {

//    readonly property
    private val _homeState = MutableLiveData<Carousel>()
    val homeState: LiveData<Carousel> get() = _homeState;
    fun loadHome(){
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val carousel = apiService.loadCarousel()
            Log.d("API Response carousel", carousel.toString())
            _homeState.postValue(carousel)
        }
    }
}