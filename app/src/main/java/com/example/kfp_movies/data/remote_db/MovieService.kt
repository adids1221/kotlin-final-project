package com.example.kfp_movies.data.remote_db

import com.example.kfp_movies.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getTrending(@Query("api_key") api_key: String): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getInTheaters(@Query("api_key") api_key: String): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") api_key: String): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") api_key: String): Response<MoviesResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(@Query("api_key") api_key: String): Response<CategoriesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getSimilarMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<SimilarMovie>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<SimilarMoviesResponse>


    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<RecommendedMoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getRecommended(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<RecommendedMovie>


    @GET("movie/{movie_id}/casts")
    suspend fun getCasts(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): Response<ActorsResponse>

    @GET("person/{person_id}")
    suspend fun getActorDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ): Response<Actor>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): Response<ReviewsResponse>

}