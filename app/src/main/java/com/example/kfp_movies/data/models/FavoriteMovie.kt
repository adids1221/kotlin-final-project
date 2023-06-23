package com.example.kfp_movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
class FavoriteMovie(
    @PrimaryKey
    val id: Int? = null,
    val poster_path: String? = null,
    val title: String? = null,
    val vote_average: Double,
    val overview: String? = null,
    val release_date: String? = null,
) {}