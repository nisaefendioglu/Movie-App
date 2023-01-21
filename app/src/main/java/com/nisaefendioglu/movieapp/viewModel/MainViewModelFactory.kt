package com.nisaefendioglu.movieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nisaefendioglu.movieapp.repository.MovieRepository

class MainViewModelFactory(
    private val repository: MovieRepository,
    private val search: String,
    private val page: Int,
    private val type: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, search, page, type) as T
    }

}
