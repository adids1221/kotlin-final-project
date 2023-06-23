package com.example.kfp_movies.ui.single_similar_movie

import com.example.kfp_movies.data.models.SimilarMovie
import com.example.kfp_movies.databinding.SingleSimilarMovieFragmentBinding


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
import dagger.hilt.android.AndroidEntryPoint
import com.example.kfp_movies.utils.*

@AndroidEntryPoint
class SingleSimilarMovieFragment : Fragment() {


    private var binding: SingleSimilarMovieFragmentBinding by autoCleared()

    private val viewModel: SingleSimilarMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingleSimilarMovieFragmentBinding.inflate(inflater, container, false)

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
                        updateSimilarMovie(it.status.data!!)
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

    private fun updateSimilarMovie(movie: SimilarMovie) {

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
}
