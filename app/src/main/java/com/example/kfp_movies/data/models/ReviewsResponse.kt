package com.example.kfp_movies.data.models

class ReviewsResponse(
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int,
    val id: Int?,
) {
}