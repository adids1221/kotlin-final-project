package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kfp_movies.data.models.FavoriteMovie

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavoriteMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>
}