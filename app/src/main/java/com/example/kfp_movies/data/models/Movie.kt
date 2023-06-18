package com.example.kfp_movies.data.models

import androidx.room.*


@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id: Int,
    var adult: Boolean,
    val backdrop_path: String?=null,
//    val genre_ids: ArrayList<Int> = arrayListOf(),
    val original_language: String?=null,
    val original_title: String?=null,
    val poster_path: String?=null,
    val title: String?=null,
    val video: Boolean,
    val vote_average: Double,
    val overview: String?=null,
    val release_date: String?=null,
    val vote_count: Int,
    val popularity: Double,
    val character: String?=null,
    val creditId: String?=null,
    val order: Int,
    var type: Int = 0,
) {}
