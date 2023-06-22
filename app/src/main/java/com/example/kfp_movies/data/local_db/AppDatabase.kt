package com.example.kfp_movies.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kfp_movies.data.models.Actor
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.models.RecommendedMovie
import com.example.kfp_movies.data.models.SimilarMovie

@Database(
    entities = [Movie::class, Actor::class, SimilarMovie::class, RecommendedMovie::class, FavouriteMovie::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun similarDao(): SimilarDao

    abstract fun recommendedDao(): RecommendedDao
    abstract fun favouriteDao():FavouriteDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "a_n_a_db"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }


}