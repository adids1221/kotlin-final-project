package com.example.kfp_movies.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kfp_movies.data.models.Actor


@Dao
interface ActorDao {
    @Query("SELECT * FROM actors")
    fun getAllActors(): LiveData<List<Actor>>

    @Query("SELECT * FROM actors WHERE id = :id")
    fun getActor(id:Int) : LiveData<Actor>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<Actor>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: Actor)
}