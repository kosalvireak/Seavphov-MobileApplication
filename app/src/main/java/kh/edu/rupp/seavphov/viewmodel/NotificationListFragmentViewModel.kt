package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.ApiState
import kh.edu.rupp.seavphov.model.Notification
import kh.edu.rupp.seavphov.model.State
import kotlinx.coroutines.launch

class NotificationListFragmentViewModel: ViewModel() {

    private val _notificationListState = MutableLiveData<ApiState<ArrayList<Notification>>>()
    val notificationListState: LiveData<ApiState<ArrayList<Notification>>> get() = _notificationListState;

    fun loadNotificationList() {
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val response = apiService.loadNotificationsList()
            try{
                if(response.isSuccess()) {
                    Log.d("Seavphov", "Load Notification Success")
                    _notificationListState.postValue(ApiState(State.success, response.data))
                } else {
                    Log.d("Seavphov", "Load Notification Error")
                    _notificationListState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading Notification: $ex")
            }
        }
    }
}