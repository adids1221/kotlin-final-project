package com.example.kfp_movies.data.repository

import com.example.kfp_movies.data.local_db.*
import androidx.lifecycle.LiveData
import com.example.kfp_movies.data.models.FavoriteMovie
import com.example.kfp_movies.data.models.Review
import com.example.kfp_movies.data.remote_db.MovieRemoteDataSource
import com.example.kfp_movies.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao,
    private val actorsLocalDataSource: ActorDao,
    private val similarLocalDataSource: SimilarDao,
    private val recommendedLocalDataSource: RecommendedDao,
    private val favoriteLocalDataSource: FavoriteDao,
    private val reviewLocalDataSource: ReviewsDao
) {

    fun getTrending(page: Int) = performFetchingAndSaving(
        { localDataSource.getAll() },
        { remoteDataSource.getTrending(page) },
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
        {actorsLocalDataSource.clearActorsTable()
            actorsLocalDataSource.insertActors(it.cast)}


    )

    fun getActor(id: Int) = performFetchingAndSaving(
        { actorsLocalDataSource.getActor(id) },
        { remoteDataSource.getActorDetails(id) },
        { actorsLocalDataSource.insertActor(it) }
    )

    fun getSimilarMovies(id: Int) = performFetchingAndSaving(
        { similarLocalDataSource.getAllSimilar() },
        { remoteDataSource.getSimilarMovies(id) },
        {similarLocalDataSource.clearSimilarTable()
            similarLocalDataSource.insertSimilarMovies(it.results)}

    )

    fun getSimilarMovie(id: Int) = performFetchingAndSaving(
        { similarLocalDataSource.getSimilarMovie(id) },
        { remoteDataSource.getSimilarMovie(id) },
        { similarLocalDataSource.insertSimilarMovie(it) }
    )

    fun getRecommendationsMovies(id: Int) = performFetchingAndSaving(
        { recommendedLocalDataSource.getAllRecommended() },
        { remoteDataSource.getRecommendations(id) },
        {recommendedLocalDataSource.clearRecommendedTable()
            recommendedLocalDataSource.insertRecommendedMovies(it.results) }
    )

    fun getRecommendedMovie(id: Int) = performFetchingAndSaving(
        { recommendedLocalDataSource.getRecommendedMovie(id) },
        { remoteDataSource.getRecommended(id) },
        { recommendedLocalDataSource.insertRecommendedMovie(it) }
    )

    suspend fun insertFavoriteMovie(movie: FavoriteMovie){
        favoriteLocalDataSource.insertFavoriteMovie(movie)
    }

    suspend fun deleteFavoriteMovie(movie:FavoriteMovie){
        favoriteLocalDataSource.deleteFavoriteMovie(movie)
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return favoriteLocalDataSource.getAllFavoriteMovies()
    }

   suspend fun isFavoriteMovie(id:Int?):Boolean{
       return favoriteLocalDataSource.isFavoriteMovie(id)
    }

    fun getReviews(id: Int) = performFetchingAndSaving(
        { reviewLocalDataSource.getAllReviews() },
        { remoteDataSource.getReviews(id) },
        {reviewLocalDataSource.clearReviewsTable()
            reviewLocalDataSource.insertReviews(it.results)
        }
    )

     fun getReview(id:String): LiveData<Review> {
        return reviewLocalDataSource.getReview(id)
    }
}