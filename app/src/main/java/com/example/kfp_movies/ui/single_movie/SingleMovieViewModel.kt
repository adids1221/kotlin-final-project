package com.example.kfp_movies.ui.single_movie

import androidx.lifecycle.*
import com.example.kfp_movies.data.models.FavoriteMovie
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
    val movie = _id.switchMap { movieRepository.getMovie(it) }

    fun setId(id: Int) {
        _id.value = id
    }

    fun addToFavorites(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.insertFavoriteMovie(favoriteMovie)
        }
    }
    fun removeFromFavorites(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.deleteFavoriteMovie(favoriteMovie)
        }
    }

    fun isFavoriteMovie(id: Int?): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            movieRepository.isFavoriteMovie(id)
            val isFavorite = withContext(Dispatchers.IO) {
                movieRepository.isFavoriteMovie(id)
            }
            resultLiveData.postValue(isFavorite )
        }
        return resultLiveData
    }




}