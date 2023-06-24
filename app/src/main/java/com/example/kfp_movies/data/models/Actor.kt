package com.example.kfp_movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors")
data class Actor (
    @PrimaryKey
    val id: Int? = null,
    val adult :Boolean?=null,
    //val also_known_as: ArrayList<String>? = null,
    val biography: String? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val gender: Int? = null,
    val homepage: String? = null,
    val imdb_id: String? = null,
    val known_for_department: String? = null,
    val name: String? = null,
    val character:String? = null,
    val place_of_birth: String? = null,
    val popularity :Double,
    val profile_path: String? = null,
    var movie_id:Int? = null
){}


