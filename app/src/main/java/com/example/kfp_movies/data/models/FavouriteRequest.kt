package com.example.kfp_movies.data.models

import com.google.gson.Gson


data class FavouriteRequest(
    val media_id: Int?,
    val favourite: Boolean,
    val media_type: String = "movie"
) {

}
