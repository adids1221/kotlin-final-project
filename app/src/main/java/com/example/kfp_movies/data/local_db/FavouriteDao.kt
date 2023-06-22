package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.Movie

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavorite(): LiveData<List<FavouriteMovie>>

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getFavoriteMovie(id: Int): LiveData<FavouriteMovie>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(favouriteMovies: List<FavouriteMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favouriteMovie: FavouriteMovie)
}