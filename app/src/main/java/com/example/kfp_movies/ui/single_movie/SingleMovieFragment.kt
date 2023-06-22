package com.example.kfp_movies.ui.single_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import dagger.hilt.android.AndroidEntryPoint
import com.example.kfp_movies.databinding.SingleMovieFragmentBinding

import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.utils.*

@AndroidEntryPoint
class SingleMovieFragment : Fragment() {


    private var binding: SingleMovieFragmentBinding by autoCleared()

    private val viewModel: SingleMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingleMovieFragmentBinding.inflate(inflater, container, false)

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
                        updateMovie(it.status.data!!)
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
        binding.showActorsBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_singleMovieFragment_to_allActorsFragment,
                bundleOf("id" to movieId)
            )
        }
        binding.showRecommendationsBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_singleMovieFragment_to_recommendationsFragment2,
                bundleOf("id" to movieId)
            )
        }
        binding.showSimilarBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_singleMovieFragment_to_similarFragment3,
                bundleOf("id" to movieId)
            )
        }

    }

    private fun updateMovie(movie: Movie) {

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
