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

    suspend fun getCasts(id:Int) =
        getResult { movieService.getCasts(id,"8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getActorDetails(id: Int) =
        getResult { movieService.getActorDetails(id,"8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun addToFavourites( favoriteRequest: FavouriteRequest) =
        getResult { movieService.addToFavourites(14497404,"6b4f2f72a9c62a07b4bd43f5400a910a",
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YjRmMmY3MmE5YzYyYTA3YjRiZDQzZjU0MDBhOTEwYSIsInN1YiI6IjYzMGY1OThiMTI0MjVjMDA5ZDdkMzk4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.t3_QdPsvCkQxMPpX2hOEqaF-MJnJL4L2mHhBm2Qzbc0",
        favoriteRequest) }

    suspend fun getFavourites()=
    getResult { movieService.getFavourites(14497404,
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2YjRmMmY3MmE5YzYyYTA3YjRiZDQzZjU0MDBhOTEwYSIsInN1YiI6IjYzMGY1OThiMTI0MjVjMDA5ZDdkMzk4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.t3_QdPsvCkQxMPpX2hOEqaF-MJnJL4L2mHhBm2Qzbc0",
        ) }
}