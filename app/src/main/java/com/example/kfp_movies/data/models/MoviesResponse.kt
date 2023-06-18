package com.example.kfp_movies.data.models

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) {}