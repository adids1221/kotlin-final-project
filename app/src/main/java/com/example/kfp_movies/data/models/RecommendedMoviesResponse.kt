package com.example.kfp_movies.data.models

data class RecommendedMoviesResponse(
    val page: Int,
    val results: List<RecommendedMovie>,
    val totalPages: Int,
    val totalResults: Int
) {
}