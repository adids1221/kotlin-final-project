package com.example.kfp_movies.ui.all_movies
import androidx.lifecycle.ViewModel
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    val movies = movieRepository.getTrending()
}