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

class BookListFragmentViewModel : ViewModel() {

    //    for BooksList
    private val _booksListState = MutableLiveData<ApiState<ArrayList<Book>>>()
    val booksListState: LiveData<ApiState<ArrayList<Book>>> get() = _booksListState;
    //    for BooksList

    fun loadBooksList() {
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val response = apiService.loadBooksList()
            try {
                if (response.isSuccess()) {
                    Log.d("Seavphov", "Load ThisWeekHighlight Success")
                    _booksListState.postValue(ApiState(State.success, response.data))
                } else {
                    Log.d("Seavphov", "Load ThisWeekHighlight Error")
                    _booksListState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                Log.e("Seavphov", "Error while loading ThisWeekHighlight: $ex")
            }
        }
    }
}