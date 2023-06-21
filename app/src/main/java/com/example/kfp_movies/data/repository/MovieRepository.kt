package com.example.kfp_movies.data.repository

import com.example.kfp_movies.data.local_db.ActorDao
import com.example.kfp_movies.data.local_db.MovieDao
import com.example.kfp_movies.data.remote_db.MovieRemoteDataSource
import com.example.kfp_movies.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao,
    private val actorsLocalDataSource: ActorDao
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
        { actorsLocalDataSource.getAllActors() },
        { remoteDataSource.getCasts(id) },
        {
            actorsLocalDataSource.clearActorsTable()
            actorsLocalDataSource.insertActors(it.cast)
        }
    )

    fun getActor(id: Int) = performFetchingAndSaving(
        { actorsLocalDataSource.getActor(id) },
        { remoteDataSource.getActorDetails(id) },
        { actorsLocalDataSource.insertActor(it) }
    )

    fun getSimilar(id: Int) = performFetchingAndSaving(
        { localDataSource.getAll() },
        { remoteDataSource.getSimilar(id) },
        { localDataSource.insertMovies(it.results) }
    )

    fun getRecommendations(id: Int) = performFetchingAndSaving(
        { localDataSource.getAll() },
        { remoteDataSource.getRecommendations(id) },
        { localDataSource.insertMovies(it.results) }
    )
}