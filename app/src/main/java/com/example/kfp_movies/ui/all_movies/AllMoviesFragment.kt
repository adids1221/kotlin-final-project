package com.example.kfp_movies.ui.all_movies

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kfp_movies.R
import com.example.kfp_movies.databinding.MoviesFragmentBinding
import com.example.kfp_movies.utils.Loading
import com.example.kfp_movies.utils.Success
import com.example.kfp_movies.utils.autoCleared
import com.example.kfp_movies.utils.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMoviesFragment : Fragment(), MoviesAdapter.MovieItemListener {

    private var binding: MoviesFragmentBinding by autoCleared()

    private val viewModel: AllMoviesViewModel by viewModels()

    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isLoading = false
        val activity = requireActivity()
        val title = getString(R.string.app_name)
        setToolbarTitle(activity, title)

        adapter = MoviesAdapter(this)
        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.moviesRv.smoothScrollToPosition(0)
        }
        binding.showFavBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_allMoviesFragment_to_allFavoriteMoviesFragment
            )
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    isLoading = true
                    binding.progressBar.isVisible = true
                }
                is Success -> {
                    if (!it.status.data.isNullOrEmpty()) {
                        isLoading = false
                        binding.progressBar.isVisible = false
                        adapter.setMovies(ArrayList(it.status.data))

                    }
                }
                is Error -> {
                    isLoading = false
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        binding.moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val isSearchEmpty = binding.search.query.toString().isEmpty()
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    val currentPage = viewModel.getCurrentPage()
                    if (isSearchEmpty) {
                        viewModel.setCurrentPage(currentPage + 1)
                    }
                }
            }
        })


    }


    override fun onMovieClick(movieId: Int) {
        findNavController().navigate(
            R.id.action_allMoviesFragment_to_singleMovieFragment,
            bundleOf("id" to movieId)
        )
    }
}
