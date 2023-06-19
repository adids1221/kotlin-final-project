package com.example.kfp_movies.ui.single_movie

import androidx.lifecycle.*
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val _id = MutableLiveData<Int>()

    val movie = _id.switchMap {movieRepository.getMovie(it)}
    fun setId(id:Int) {
        _id.value = id
    }




}