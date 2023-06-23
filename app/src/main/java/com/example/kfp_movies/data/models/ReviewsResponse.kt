package com.example.kfp_movies.data.models

class ReviewsResponse(
    val id: Int?,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int,

) {
}