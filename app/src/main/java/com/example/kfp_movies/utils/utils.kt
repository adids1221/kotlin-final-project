package com.example.kfp_movies.utils

import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity
import androidx.appcompat.widget.Toolbar
import com.example.kfp_movies.R
import com.example.kfp_movies.ui.MainActivity

fun getRating(rating: Double): Float {
    val maxRating = 5.0
    val stepSize = 0.5
    val numOfStars = 5

    val numStars = (rating / (maxRating / numOfStars)).toFloat()

    return (numStars * stepSize).toFloat()
}

fun reformatDate(dateString: String?): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    if (dateString != null && dateString != "") {
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
    return ""
}

fun setToolbarTitle(activity: Activity, title: String) {
    val mainActivity = activity as? MainActivity
    val toolbar = mainActivity?.findViewById<Toolbar>(R.id.toolbar)
    toolbar?.title = title
}