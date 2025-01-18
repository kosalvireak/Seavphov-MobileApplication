package kh.edu.rupp.seavphov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.ApiResponse
import kh.edu.rupp.seavphov.model.ApiState
import kh.edu.rupp.seavphov.model.LoginResponse
import kh.edu.rupp.seavphov.model.State
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {

    private val _loginResponseState = MutableLiveData<ApiState<ApiResponse<LoginResponse>>>()
    val loginResponseState: LiveData<ApiState<ApiResponse<LoginResponse>>> get() = _loginResponseState;

    fun login(gmail: String, password: String) {

        Log.d("Seavphov", "login gmail $gmail password $password")
        val apiService = ApiManager.getSeavphovApiService()
        viewModelScope.launch {
            try {
                val loginResponse = apiService.login(gmail, password)
                Log.d("Seavphov", "Login loginResponse $loginResponse")
                if (loginResponse.isSuccess()) {
                    Log.d("Seavphov", "Login Success")
                    _loginResponseState.postValue(ApiState(State.success, loginResponse))
                } else {
                    Log.d("Seavphov", "Login Error")
                    _loginResponseState.postValue(ApiState(State.error, loginResponse))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while login: $ex")
            }
        }
    }
}