package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.models.SimilarMovie

@Dao
interface SimilarDao {

    @Query("SELECT * FROM similar_movies")
    fun getAllSimilar(): LiveData<List<SimilarMovie>>

    @Query("SELECT * FROM similar_movies WHERE id = :id")
    fun getSimilarMovie(id:Int) : LiveData<SimilarMovie>

    @Query("DELETE FROM similar_movies")
    suspend fun clearSimilarTable()



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMovies(movies: List<SimilarMovie>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMovie(movie: SimilarMovie)
}