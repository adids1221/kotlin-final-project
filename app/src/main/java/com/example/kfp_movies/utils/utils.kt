package com.example.kfp_movies.utils

import java.text.SimpleDateFormat
import java.util.*

fun getRating(rating: Double): Float {
    val maxRating = 5.0
    val stepSize = 0.5
    val numOfStars = 5

    val numStars = (rating / (maxRating / numOfStars)).toFloat()

    return (numStars * stepSize).toFloat()
}

fun reformatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}