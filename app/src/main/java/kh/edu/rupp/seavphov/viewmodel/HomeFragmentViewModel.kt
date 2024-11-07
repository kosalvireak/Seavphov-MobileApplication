package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.ApiState
import kh.edu.rupp.seavphov.model.Carousel
import kh.edu.rupp.seavphov.model.State
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {

//    readonly property
    private val _homeState = MutableLiveData<ApiState<Carousel>>()
    val homeState: LiveData<ApiState<Carousel>> get() = _homeState;
    fun loadHome(){
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val carouselResponse = apiService.loadCarousel()
            try {
                if(carouselResponse.isSuccess()){
                    Log.d("Seavphov","Load Carousel Success")
                    _homeState.postValue(ApiState(State.success, carouselResponse.data))
                } else {
                    Log.d("Seavphov","Load Carousel Error")
                    _homeState.postValue(ApiState(State.error, null))
                }
            }catch (ex: Exception){
                Log.e("Seavphov", "Error while loading profile: $ex")
            }

        }
    }
}