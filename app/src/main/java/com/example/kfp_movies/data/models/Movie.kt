package com.example.kfp_movies.data.models

import androidx.room.*


@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id: Int,
    var adult: Boolean,
    val backdropPath: String,
//    val genreIds: ArrayList<Int> = arrayListOf(),
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String,
    val voteCount: Int,
    val popularity: Double,
    val character: String,
    val creditId: String,
    val order: Int,
    var type: Int = 0,
) {}
