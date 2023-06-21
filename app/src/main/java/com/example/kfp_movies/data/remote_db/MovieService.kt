package com.example.kfp_movies.data.remote_db
import com.example.kfp_movies.data.models.*
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.*

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
/*
    @GET("movie/{movie_id}/casts")
    suspend fun getCasts(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): Response<CastAndCrewResponse>

    @GET("person/{person_id}")
    suspend fun getCastCrewDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ): Response<CastCrewDetailsResponse>

*/
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

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") api_key: String
    ): Response<SearchResultResponse>


    @POST("account/{account_id}/favorite")
    suspend fun addToFavourites(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Header("Authorization") bearerToken: String,
        @Body favoriteRequest: FavouriteRequest
    ): Response<FavouriteResponse>
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavourites(
        @Path("account_id") accountId: Int,
        //@Query("api_key") apiKey: String,
        @Header("Authorization") bearerToken: String,
    ): Response<MoviesResponse>

}