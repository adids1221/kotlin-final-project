package com.example.kfp_movies.data.repository

import com.example.kfp_movies.data.local_db.ActorDao
import com.example.kfp_movies.data.local_db.MovieDao
import com.example.kfp_movies.data.local_db.SimilarDao
import com.example.kfp_movies.data.remote_db.MovieRemoteDataSource
import com.example.kfp_movies.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao,
    private val actorsLocalDataSource: ActorDao,
    private val similarsLocalDataSource:SimilarDao
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

    fun getSimilarMovies(id: Int) = performFetchingAndSaving(
        { similarsLocalDataSource.getAllSimilar() },
        { remoteDataSource.getSimilar(id) },
        {similarsLocalDataSource.clearSimilarTable()
         similarsLocalDataSource.insertSimilarMovies(it.results) }
    )
   fun getSimilarMovie(id: Int) = performFetchingAndSaving(
        { similarsLocalDataSource.getSimilarMovie(id) },
        { remoteDataSource.getSimilarMovie(id) },
        { similarsLocalDataSource.insertSimilarMovie(it) }
    )

    /*fun getRecommendations(id: Int) = performFetchingAndSaving(
        { similarsLocalDataSource.getAll() },
        { remoteDataSource.getRecommendations(id) },
        { similarsLocalDataSource.insertMovies(it.results) }
    )*/
}