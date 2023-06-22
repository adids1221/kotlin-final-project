package com.example.kfp_movies.ui.all_favorite_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kfp_movies.data.local_db.FavoriteDao
import com.example.kfp_movies.data.models.FavoriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllFavoriteMoviesViewModel @Inject constructor(
    private val favoriteMovieDao: FavoriteDao
) : ViewModel() {
    val movies: LiveData<List<FavoriteMovie>> = favoriteMovieDao.getAllFavoriteMovies()
}