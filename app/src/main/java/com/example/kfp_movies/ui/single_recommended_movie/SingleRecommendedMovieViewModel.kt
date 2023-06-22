package com.example.kfp_movies.ui.single_recommended_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleRecommendedMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val movie = _id.switchMap { movieRepository.getRecommendedMovie(it) }
    fun setId(id: Int) {
        _id.value = id
    }
}