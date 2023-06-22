package com.example.kfp_movies.data.remote_db

import com.example.kfp_movies.data.models.FavouriteRequest
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    suspend fun getTrending() =
        getResult { movieService.getTrending("8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getMovie(id: Int) =
        getResult { movieService.getMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getCasts(id: Int) =
        getResult { movieService.getCasts(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getActorDetails(id: Int) =
        getResult { movieService.getActorDetails(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getSimilar(id: Int) =
        getResult { movieService.getSimilar(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getSimilarMovie(id: Int) =
        getResult { movieService.getSimilarMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getRecommendations(id: Int) =
        getResult { movieService.getRecommendations(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getRecommended(id: Int) =
        getResult { movieService.getRecommended(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun addToFavorites(favoriteRequest: FavouriteRequest) =
        getResult {
            movieService.addToFavorites(
                18169098,
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZTViYTg0OTVkYWRkYzZkMjQzOGJmYzUzNWRhYTY0NiIsInN1YiI6IjY0MGM1ZTY1MmEwOWJjMDBjNTJkMDAwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.15Yz5eQirfI4cQmt5YmO6_3JerdSAG6FFdvmztDepZc",
                favoriteRequest
            )
        }

    suspend fun getFavorites() =
        getResult {
            movieService.getFavorites(
                14497404,
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YjRmMmY3MmE5YzYyYTA3YjRiZDQzZjU0MDBhOTEwYSIsInN1YiI6IjYzMGY1OThiMTI0MjVjMDA5ZDdkMzk4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.t3_QdPsvCkQxMPpX2hOEqaF-MJnJL4L2mHhBm2Qzbc0",
            )
        }
}