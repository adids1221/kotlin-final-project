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
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getMovie(id:Int) : LiveData<Movie>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(favouriteMovies: List<Movie>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(favouriteMovie: Movie)
}