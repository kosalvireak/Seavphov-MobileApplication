package kh.edu.rupp.seavphov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {

    fun loadHome(){
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val book = apiService.loadBooks()
        }
    }
}