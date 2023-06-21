package com.example.kfp_movies.data.local_db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kfp_movies.data.models.Actor
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.Movie

@Database(entities = [Movie::class, Actor::class,FavouriteMovie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun favouriteDao():FavouriteDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movies"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }


}