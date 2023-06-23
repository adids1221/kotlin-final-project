package com.example.kfp_movies.data.models

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey
    @NonNull
    val id: String = "",
    var url: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    val content: String? = null,
    val author: String?,
    @Embedded
    val author_details: AuthorDetails?,
    ) {
}


