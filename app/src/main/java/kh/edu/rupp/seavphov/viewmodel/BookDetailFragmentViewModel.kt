package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.ApiState
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.model.State
import kotlinx.coroutines.launch

class BookDetailFragmentViewModel : ViewModel() {

    private val _bookDetailState = MutableLiveData<ApiState<BookDetail>>()
    val bookDetailState: LiveData<ApiState<BookDetail>> get() = _bookDetailState

    fun loadBookDetail() {
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val response = apiService.loadBookDetail()
            Log.d("Seavphov", "loadBookDetail: $response")
            try {
                if (response.isSuccess()) {
                    Log.d("Seavphov", "Load NewestAddition Success")
                    _bookDetailState.postValue(ApiState(State.success, response.data))
                } else {
                    Log.d("Seavphov", "Load NewestAddition Error")
                    _bookDetailState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading newestAddition: $ex")
            }
        }
    }
}