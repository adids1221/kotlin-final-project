package com.example.kfp_movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommended_movies")
class RecommendedMovie(
    @PrimaryKey
    val id: Int? = null,
    var adult: Boolean? = null,
    val backdrop_path: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val poster_path: String? = null,
    val title: String? = null,
    val video: Boolean,
    val vote_average: Double,
    val overview: String? = null,
    val release_date: String? = null,
    val vote_count: Int,
    val popularity: Double,
    val order: Int,
) {
}