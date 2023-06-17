package com.example.kfp_movies.data.models

data class CastAndCrewResponse(
    var id: Int? = null,
    var cast: ArrayList<Cast> = arrayListOf(),
    var crew: ArrayList<Crew> = arrayListOf()
) {
}