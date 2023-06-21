package com.example.kfp_movies.ui.all_favourites

import FavouritesAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kfp_movies.databinding.FavouritesFragmentBinding
import com.example.kfp_movies.utils.Loading
import com.example.kfp_movies.utils.Success
import com.example.kfp_movies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavouritesFragment : Fragment(), FavouritesAdapter.FavouriteItemListener {

    private var binding: FavouritesFragmentBinding by autoCleared()

    private val viewModel: AllFavouritesViewModel by viewModels()

    private lateinit var adapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavouritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavouritesAdapter(this)
        binding.favouritesRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.favouritesRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.favouritesRv.smoothScrollToPosition(0)
        }

        viewModel.favouriteMovies.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if (!it.status.data.isNullOrEmpty()) {
                        binding.progressBar.isVisible = false
                        adapter.setFavourites(ArrayList(it.status.data))

                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        arguments?.getInt("id")?.let {
            Log.d(it.toString(), it.toString())


            //viewModel.setId(it)
        }

    }




    override fun onMovieClick(movieId: Int) {
        TODO("Not yet implemented")
    }
}