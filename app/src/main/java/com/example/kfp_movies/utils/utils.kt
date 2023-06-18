package com.example.kfp_movies.utils


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import androidx.viewbinding.ViewBinding

import android.widget.EditText
import com.example.kfp_movies.R
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

data class MovieData(
    val title: String?,
    val releaseDate: String?,
    val description: String?,
    val ratingText: String?,
    val imageSource: String?
)

fun getRating(rating: Double): Float {
    val maxRating = 5.0
    val stepSize = 0.5
    val numOfStars = 5

    val numStars = (rating / (maxRating / numOfStars)).toFloat()

    return (numStars * stepSize).toFloat()
}

fun validTitle(context: Context, title: String): String? {
    return if (title.isEmpty()) context.getString(R.string.title_helper_text) else null
}

fun validReleaseDate(context: Context, releaseDate: String): String? {
    return if (releaseDate.isEmpty()) context.getString(R.string.release_date_helper_text) else null
}

fun validDescription(context: Context, description: String): String? {
    return if (description.isEmpty()) context.getString(R.string.description_helper_text) else null
}

fun isFutureDate(dateStr: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
    val currentDate = Date()
    return try {
        val date = dateFormat.parse(dateStr)
        date?.after(currentDate) ?: false
    } catch (e: Exception) {
        false
    }
}

fun validRating(
    context: Context,
    movieRating: String,
    isEnabled: Boolean,
    releaseDate: String
): String? {
    val rating = movieRating.toDoubleOrNull()
    if (isEnabled) {
        if (rating == null && isFutureDate(releaseDate)) {
            return null
        } else if (rating != null && isFutureDate(releaseDate)) {
            return context.getString(R.string.rating_disabled_helper_text)
        } else if (rating != null && (rating < 0 || rating > 10)) {
            return context.getString(R.string.rating_helper_text)
        }
    }
    return null
}







fun EditText.addTitleFocusListener(context: Context, titleContainer: TextInputLayout) {
    this.setOnFocusChangeListener { _, focused ->
        if (!focused) {
            val helperText = validTitle(context, this.text.toString())
            titleContainer.helperText = helperText
        }
    }
}

fun EditText.addReleaseDateFocusListener(
    context: Context,
    datePickerDialog: DatePickerDialog?,
    releaseDateContainer: TextInputLayout
) {
    this.setOnFocusChangeListener { _, focused ->
        if (focused) {
            datePickerDialog?.show()
        } else {
            val helperText = validReleaseDate(context, this.text.toString())
            releaseDateContainer.helperText = helperText
        }
    }
}

fun EditText.addDescriptionFocusListener(context: Context, descriptionContainer: TextInputLayout) {
    this.setOnFocusChangeListener { _, focused ->
        if (!focused) {
            val helperText = validDescription(context, this.text.toString())
            descriptionContainer.helperText = helperText
        }
    }
}

fun EditText.addRatingFocusListener(
    context: Context,
    releaseDate: EditText,
    ratingContainer: TextInputLayout
) {
    this.setOnFocusChangeListener { view, focused ->
        if (!focused) {
            val helperText = validRating(
                context,
                this.text.toString(),
                this.isEnabled,
                releaseDate.text.toString()
            )
            ratingContainer.helperText = helperText
        }
    }
}
