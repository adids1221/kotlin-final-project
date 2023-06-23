package com.example.kfp_movies.ui.single_movie

import androidx.lifecycle.*
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val movie = _id.switchMap { movieRepository.getMovie(it) }
    val reviews = _id.switchMap { movieRepository.getReviews(it) }
    fun setId(id: Int) {
        _id.value = id
    }


}