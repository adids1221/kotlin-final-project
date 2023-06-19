package com.example.kfp_movies.ui.single_movie
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.example.kfp_movies.databinding.SingleMovieFragmentBinding
import com.example.kfp_movies.utils.Loading
import com.example.kfp_movies.utils.Success
import com.example.kfp_movies.utils.Error
import com.example.kfp_movies.utils.autoCleared

import com.example.kfp_movies.data.models.Movie

@AndroidEntryPoint
class SingleMovieFragment : Fragment() {


    private var binding: SingleMovieFragmentBinding by autoCleared()

    private val viewModel:SingleMovieViewModel by viewModels()

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
            when(it.status) {
                is Loading-> binding.progressBar.isVisible = true
                is Success -> {
                    if(it.status.data!=null) {
                        binding.progressBar.isVisible = false
                        updateMovie(it.status.data!!)
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(),it.status.message,Toast.LENGTH_SHORT).show()
                }

            }
        }

        arguments?.getInt("id")?.let {
            Log.d(it.toString(),it.toString())

            viewModel.setId(it)
        }

    }

    private fun updateMovie(movie: Movie) {

        binding.movieTitle.text = movie.title
        binding.movieDescription.text= movie.overview
        binding.movieReleaseDate.text= movie.release_date
        //Glide.with(requireContext()).load(movie.poster_path).circleCrop().into(binding.moviePoster)
       /* binding.gender.text = character.gender
        binding.species.text = character.species
        binding.status.text = character.status
        */

    }
}
