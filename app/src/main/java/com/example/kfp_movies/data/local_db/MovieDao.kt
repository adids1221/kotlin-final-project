package com.example.kfp_movies.data.local_db
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getAllTrending(type: Int? = 0): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getAllUpcoming(type: Int = 1): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getAllInTheaters(type: Int = 2): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInTheaters(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
}