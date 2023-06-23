package com.example.kfp_movies.data.remote_db

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    suspend fun getTrending() =
        getResult { movieService.getTrending("8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getMovie(id: Int) =
        getResult { movieService.getMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getCasts(id: Int) =
        getResult { movieService.getCasts(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getActorDetails(id: Int) =
        getResult { movieService.getActorDetails(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getSimilar(id: Int) =
        getResult { movieService.getSimilar(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getSimilarMovie(id: Int) =
        getResult { movieService.getSimilarMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getRecommendations(id: Int) =
        getResult { movieService.getRecommendations(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getRecommended(id: Int) =
        getResult { movieService.getRecommended(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getReviews(id: Int) =
        getResult { movieService.getReviews(id, "8e5ba8495daddc6d2438bfc535daa646") }
}