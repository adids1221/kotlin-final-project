package com.example.kfp_movies.data.models

data class SimilarMoviesResponse(
    val page: Int,
    var results: List<SimilarMovie>,
    val total_pages: Int,
    val total_results: Int
) {}