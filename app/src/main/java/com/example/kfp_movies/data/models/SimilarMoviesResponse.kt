package com.example.kfp_movies.data.models

data class SimilarMoviesResponse(
    val page: Int,
    val results: List<SimilarMovie>,
    val totalPages: Int,
    val totalResults: Int
) {}