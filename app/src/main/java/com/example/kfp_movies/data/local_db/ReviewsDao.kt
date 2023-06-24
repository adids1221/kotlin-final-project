package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.Review
import com.example.kfp_movies.data.models.SimilarMovie

@Dao
interface ReviewsDao {
    @Query("SELECT * FROM reviews ")
    fun getAllReviews(): LiveData<List<Review>>

    @Query("SELECT * FROM reviews WHERE id = :id")
    fun getReview(id: String): LiveData<Review>

    @Query("DELETE FROM reviews")
    suspend fun clearReviewsTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)
}