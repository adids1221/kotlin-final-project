package com.example.kfp_movies.ui.single_movie_similar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    val movies = _id.switchMap { movieRepository.getSimilar(it) }
}