package com.example.kfp_movies.ui.all_recommendation_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    val movies = _id.switchMap { movieRepository.getSimilarMovies(it) }
}