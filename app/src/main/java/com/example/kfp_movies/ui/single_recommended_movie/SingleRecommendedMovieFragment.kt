package com.example.kfp_movies.ui.single_recommended_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.FavoriteMovie
import com.example.kfp_movies.data.models.RecommendedMovie
import com.example.kfp_movies.databinding.SingleRecommendedMovieBinding
import com.example.kfp_movies.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleRecommendedMovieFragment : Fragment() {


    private var binding: SingleRecommendedMovieBinding by autoCleared()

    private val viewModel: SingleRecommendedMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingleRecommendedMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        lateinit var title: String

        viewModel.movie.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if (it.status.data != null) {
                        binding.progressBar.isVisible = false
                        title = it.status.data.title.toString()
                        setToolbarTitle(activity, title)
                        updateRecommendedMovie(it.status.data!!)
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

        arguments?.getInt("id")?.let {
            viewModel.setId(it)
        }

        val movieId = arguments?.getInt("id")

        viewModel.isFavoriteMovie(movieId).observe(viewLifecycleOwner) { isFavorite ->
            binding.favStar.isChecked = isFavorite
        }
    }

    private fun updateRecommendedMovie(movie: RecommendedMovie) {
        val favoriteMovie = FavoriteMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            release_date = movie.release_date,
            vote_average = movie.vote_average,
            poster_path = movie.poster_path
        )


        binding.favStar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addMovieToFavorites(favoriteMovie)
            } else {
                deleteFavoriteMovie(favoriteMovie)
            }
        }

        binding.movieTitle.text = movie.title
        binding.movieDescription.text = movie.overview
        binding.movieReleaseDate.text = movie.release_date?.let { reformatDate(it) }
        binding.itemRatingBar.rating = movie.vote_average?.let { getRating(it) }!!
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.moviePoster)
    }

    private fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModel.addToFavorites(movie)
        Toast.makeText(requireContext(), getString(R.string.add_to_fav), Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteMovie(movie: FavoriteMovie) {
        viewModel.removeFromFavorites(movie)
        Toast.makeText(requireContext(), getString(R.string.remove_from_fav), Toast.LENGTH_SHORT)
            .show()
    }
}
