package com.example.kfp_movies.data.remote_db

import com.example.kfp_movies.data.models.*
import com.example.kfp_movies.utils.Resource
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    suspend fun getTrending(page: Int) =
        getResult { movieService.getTrending("8e5ba8495daddc6d2438bfc535daa646",page) }

    suspend fun getMovie(id: Int) =
        getResult { movieService.getMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getCasts(id: Int):Resource<ActorsResponse> {
        val response = getResult { movieService.getCasts(id, "8e5ba8495daddc6d2438bfc535daa646") }
        response.status.data?.cast?.map { actor -> actor.movie_id = id  }

        return response
    }

    suspend fun getActorDetails(id: Int) =
        getResult { movieService.getActorDetails(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getSimilarMovies(id: Int):Resource<SimilarMoviesResponse> {
        var response =
            getResult { movieService.getSimilarMovies(id, "8e5ba8495daddc6d2438bfc535daa646") }
        response.status.data?.results?.map {movie -> movie.related_movie_id = id  }
        return response
    }
    suspend fun getSimilarMovie(id: Int) =
        getResult { movieService.getSimilarMovie(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getRecommendations(id: Int):Resource<RecommendedMoviesResponse> {
        var response =
            getResult { movieService.getRecommendations(id, "8e5ba8495daddc6d2438bfc535daa646") }
        response.status.data?.results?.map {movie -> movie.related_movie_id = id  }
        return response
    }
    suspend fun getRecommended(id: Int) =
        getResult { movieService.getRecommended(id, "8e5ba8495daddc6d2438bfc535daa646") }

    suspend fun getReviews(id: Int):Resource<ReviewsResponse> {
        var response = getResult { movieService.getReviews(id, "8e5ba8495daddc6d2438bfc535daa646")}
            response.status.data?.results?.map {review -> review.related_movie_id = id  }
            return response
    }
}