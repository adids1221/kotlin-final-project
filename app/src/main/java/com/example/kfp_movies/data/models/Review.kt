package com.example.kfp_movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
class Review(
    @PrimaryKey
    val id: String? = null,
    var url: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    val content: String? = null,
    val author: String?,
    val author_details: AuthorDetails?,

    ) {
}

data class AuthorDetails(
    val name: String?,
    val username: String,
    val avatar_path: String?,
    val rating: Double
)