package com.example.kfp_movies.data.repository

import com.example.kfp_movies.data.local_db.ActorDao
import com.example.kfp_movies.data.local_db.FavouriteDao
import com.example.kfp_movies.data.local_db.MovieDao
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.FavouriteRequest
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.remote_db.MovieRemoteDataSource
import com.example.kfp_movies.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao,
    private val actorsLocalDataSource: ActorDao,
    private val favouritesLocalDataSource: FavouriteDao
) {

    fun getTrending() = performFetchingAndSaving(
        { localDataSource.getAll() },
        { remoteDataSource.getTrending() },
        { localDataSource.insertMovies(it.results) }
    )

    fun getMovie(id: Int) = performFetchingAndSaving(
        { localDataSource.getMovie(id) },
        { remoteDataSource.getMovie(id) },
        { localDataSource.insertMovie(it) }
    )

    fun getCasts(id: Int) = performFetchingAndSaving(
        {actorsLocalDataSource.getAllActors()},
        {remoteDataSource.getCasts(id)},
        {actorsLocalDataSource.clearActorsTable()
          actorsLocalDataSource.insertActors(it.cast)
        }
    )

    fun getActor(id: Int) = performFetchingAndSaving(
        {actorsLocalDataSource.getActor(id)},
        {remoteDataSource.getActorDetails(id)},
        {actorsLocalDataSource.insertActor(it)}
    )

    /*fun addToFavourite(id:Int,favouriteRequest:FavouriteRequest) = performFetchingAndSaving(
        { favouritesLocalDataSource.getMovie(id) },
        {remoteDataSource.getMovie(id)},
        {remoteDataSource.addToFavourites(favouriteRequest)
            favouritesLocalDataSource.insertMovie(it) }
    )*/

     fun getFavourites() = performFetchingAndSaving(
         { favouritesLocalDataSource.getAll() },
         { remoteDataSource.getFavourites() },
         { favouritesLocalDataSource.insertMovies(it.results) }
     )
     suspend fun insertToFavourites(favouriteMovie: Movie) =
        favouritesLocalDataSource.insertMovie(favouriteMovie)

     fun getFavour() = favouritesLocalDataSource.getAll()

    suspend fun addToFavourites(favouriteRequest: FavouriteRequest) =
        remoteDataSource.addToFavourites(favouriteRequest)


}