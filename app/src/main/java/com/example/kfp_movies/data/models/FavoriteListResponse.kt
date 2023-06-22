package com.example.kfp_movies.data.models

data class FavoriteListResponse(
    val page: Int,
    val results: List<FavouriteMovie>,
    val totalPages: Int,
    val totalResults: Int
) {
}