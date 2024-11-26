package kh.edu.rupp.seavphov.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.seavphov.api.ApiManager
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.BookDetail
import kotlinx.coroutines.launch

class BookDetailFragmentViewModel: ViewModel() {

    private val _bookDetailState = MutableLiveData<BookDetail>()
    val bookDetailState: LiveData<BookDetail> get() = _bookDetailState


    fun loadBookDetail() {
        val apiService = ApiManager.getApiService()
        viewModelScope.launch {
            val bookDetail = apiService.loadBookDetail()
            _bookDetailState.postValue(bookDetail)
        }
    }
}