package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kfp_movies.data.models.FavoriteMovie
import com.example.kfp_movies.data.models.Movie

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    suspend fun isFavoriteMovie(id: Int?): Boolean



}