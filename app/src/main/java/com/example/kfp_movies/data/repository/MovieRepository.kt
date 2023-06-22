package com.example.kfp_movies.data.repository

import com.example.kfp_movies.data.local_db.ActorDao
import com.example.kfp_movies.data.local_db.FavouriteDao
import com.example.kfp_movies.data.local_db.MovieDao
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.FavouriteRequest
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.local_db.RecommendedDao
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
    private val favouritesLocalDataSource: FavouriteDao,
    private val similarLocalDataSource: SimilarDao,
    private val recommendedLocalDataSource: RecommendedDao

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

    /*fun addToFavourite(id:Int,favouriteRequest:FavouriteRequest) = performFetchingAndSaving(
        { favouritesLocalDataSource.getMovie(id) },
        {remoteDataSource.getMovie(id)},
        {remoteDataSource.addToFavourites(favouriteRequest)
            favouritesLocalDataSource.insertMovie(it) }
    )*/

     fun getFavorites() = performFetchingAndSaving(
         { favouritesLocalDataSource.getAllFavorite() },
         { remoteDataSource.getFavorites() },
         { favouritesLocalDataSource.insertFavoriteMovies(it.results) }
     )
//     suspend fun insertToFavourites(favouriteMovie: Movie) =
//        favouritesLocalDataSource.insertMovie(favouriteMovie.id)

//     fun getFavour() = favouritesLocalDataSource.getAll()

    suspend fun addToFavorites(favouriteRequest: FavouriteRequest) =
        remoteDataSource.addToFavorites(favouriteRequest)



    fun getSimilarMovies(id: Int) = performFetchingAndSaving(
        { similarLocalDataSource.getAllSimilar() },
        { remoteDataSource.getSimilar(id) },
        {
            similarLocalDataSource.clearSimilarTable()
            similarLocalDataSource.insertSimilarMovies(it.results)
        }
    )

    fun getSimilarMovie(id: Int) = performFetchingAndSaving(
        { similarLocalDataSource.getSimilarMovie(id) },
        { remoteDataSource.getSimilarMovie(id) },
        { similarLocalDataSource.insertSimilarMovie(it) }
    )

    fun getRecommendationsMovies(id: Int) = performFetchingAndSaving(
        { recommendedLocalDataSource.getAllRecommended() },
        { remoteDataSource.getRecommendations(id) },
        {
            recommendedLocalDataSource.clearRecommendedTable()
            recommendedLocalDataSource.insertRecommendedMovies(it.results)
        }
    )

    fun getRecommendedMovie(id: Int) = performFetchingAndSaving(
        { recommendedLocalDataSource.getRecommendedMovie(id) },
        { remoteDataSource.getRecommended(id) },
        { recommendedLocalDataSource.insertRecommendedMovie(it) }
    )
}