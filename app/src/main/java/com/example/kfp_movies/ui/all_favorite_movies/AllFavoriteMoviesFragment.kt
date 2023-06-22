package com.example.kfp_movies.ui.all_favorite_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kfp_movies.R
import com.example.kfp_movies.databinding.FavoriteFragmentBinding
import com.example.kfp_movies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoriteMoviesFragment : Fragment(), AllFavoriteMoviesAdapter.FavoriteItemListener {

    private var binding: FavoriteFragmentBinding by autoCleared()

    private val viewModel: AllFavoriteMoviesViewModel by viewModels()

    private lateinit var adapter: AllFavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AllFavoriteMoviesAdapter(this)
        binding.favoriteRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.favoriteRv.smoothScrollToPosition(0)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.setMovies(it)
        }
    }


    override fun onMovieClick(movieId: Int) {
        findNavController().navigate(
            R.id.action_allFavoriteMoviesFragment_to_singleMovieFragment,
            bundleOf("id" to movieId)
        )
    }
}