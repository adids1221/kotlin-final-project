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

        viewModel.movie.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if (it.status.data != null) {
                        binding.progressBar.isVisible = false
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
    }

    private fun updateRecommendedMovie(movie: RecommendedMovie) {
        binding.movieTitle.text = movie.title
        binding.movieDescription.text = movie.overview
        binding.movieReleaseDate.text = movie.release_date
        binding.itemRatingBar.rating = movie.vote_average?.let { getRating(it) }!!
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.moviePoster)
    }
}
