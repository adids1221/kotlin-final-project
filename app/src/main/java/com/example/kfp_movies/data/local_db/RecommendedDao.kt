package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.RecommendedMovie
import com.example.kfp_movies.data.models.SimilarMovie

@Dao
interface RecommendedDao {
    @Query("SELECT * FROM recommended_movies where related_movie_id = :id")
    fun getAllRecommended(id: Int): LiveData<List<RecommendedMovie>>


    @Query("SELECT * FROM recommended_movies WHERE id = :id")
    fun getRecommendedMovie(id: Int): LiveData<RecommendedMovie>

    @Query("DELETE FROM recommended_movies")
    suspend fun clearRecommendedTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedMovies(movies: List<RecommendedMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedMovie(movie: RecommendedMovie)
}