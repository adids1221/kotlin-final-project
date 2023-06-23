package com.example.kfp_movies.ui.all_favorite_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kfp_movies.data.local_db.FavoriteDao
import com.example.kfp_movies.data.models.FavoriteMovie
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFavoriteMoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val movies = movieRepository.getAllFavoriteMovies()
    fun removeFromFavorites(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.deleteFavoriteMovie(favoriteMovie)
        }
    }
}