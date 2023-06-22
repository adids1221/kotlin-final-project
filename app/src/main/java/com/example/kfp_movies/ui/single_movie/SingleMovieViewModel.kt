package com.example.kfp_movies.ui.single_movie

import androidx.lifecycle.*
import com.example.kfp_movies.data.models.FavouriteRequest
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val movie = _id.switchMap { movieRepository.getMovie(it) }

    fun setId(id: Int) {
        _id.value = id
    }

    /*suspend fun insertToFavourite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.insertToFavourites(movie)
        }


    }*/
  /*suspend fun insertToFavourite(movie: Movie) {
        movieRepository.insertToFavourites(movie)

    }*/
    fun addToFavorites(favouriteRequest: FavouriteRequest) {
        viewModelScope.launch {
            movieRepository.addToFavorites(favouriteRequest)
        }
    }
}