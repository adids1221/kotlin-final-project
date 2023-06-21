package com.example.kfp_movies.ui.all_favourites


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFavouritesViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    val favouriteMovies= movieRepository.getFavourites()
    //val favouriteMovies:LiveData<List<Movie>>? = movieRepository.getFavour()







}


