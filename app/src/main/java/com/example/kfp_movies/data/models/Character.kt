package com.example.kfp_movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: Int,
    val name:String,
    val status:String,
    val species:String,
    val type:String,
    val gender:String,
    val image:String,
    val url:String,
    val created:String
) {}