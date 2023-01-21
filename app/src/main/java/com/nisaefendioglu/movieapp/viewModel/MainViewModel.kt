package com.nisaefendioglu.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.nisaefendioglu.movieapp.model.DataModel
import com.nisaefendioglu.movieapp.repository.MovieRepository

class MainViewModel(
    private val repository: MovieRepository,
    search: String,
    page: Int,
    type: String,
) : ViewModel() {

    val showLoader = MutableLiveData<Boolean>()

    private var isLastPage = false

    private val dataList = mutableListOf<DataModel>()
    private val _showsLiveData = MutableLiveData<List<DataModel>?>()
    val showsLiveData = _showsLiveData

    init {
        repository.getData(search, page, type)
        repository.movieErrorLiveData.observeForever {
            showLoader.value = false
        }

        repository.movieLiveData.observeForever {
            it?.let { data ->
                val list = data.search ?: listOf()
                dataList.addAll(list)
                _showsLiveData.value = it.search
                isLastPage = (it.totalResults?.toInt() ?: 0) <= dataList.size
            }.run {
                Log.d("Show data", Unit.toString())
            }
        }
    }

    fun getData(search: String, page: Int, type: String) {
        if (!isLoading() && !isLastPage()) {
            showLoader.value = true
            repository.getData(search, page, type)
        }
    }

    private fun isLastPage(): Boolean {
        return isLastPage
    }

    private fun isLoading(): Boolean {
        return showLoader.value ?: false
    }
}
