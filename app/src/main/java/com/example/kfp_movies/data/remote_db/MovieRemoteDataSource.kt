package com.example.kfp_movies.data.remote_db

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    suspend fun getTrending() = getResult { movieService.getTrending("8e5ba8495daddc6d2438bfc535daa646") }
}