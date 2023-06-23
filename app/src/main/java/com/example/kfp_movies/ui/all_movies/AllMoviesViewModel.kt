package com.example.kfp_movies.ui.all_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {
    private val currentPage = MutableLiveData<Int>()

    init {
        currentPage.value = 1 // Set initial page number
    }

    val movies = currentPage.switchMap { movieRepository.getTrending(it) }

    fun setCurrentPage(page: Int) {
        currentPage.value = page
    }

    fun getCurrentPage(): Int {
        return currentPage.value ?: 1 // Return the current page value or default to 1 if null
    }
}