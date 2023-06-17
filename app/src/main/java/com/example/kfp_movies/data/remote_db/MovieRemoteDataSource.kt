package com.example.kfp_movies.data.remote_db

import com.example.kfp_movies.BuildConfig
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    var API_KEY: String = BuildConfig.API_KEY
    suspend fun getTrending() = getResult { movieService.getTrending(API_KEY) }
}