package com.example.kfp_movies.ui.all_recommendation_movies

import android.os.Bundle
import android.util.Log
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
import com.example.kfp_movies.R
import com.example.kfp_movies.databinding.RecommendationsFragmentBinding
import com.example.kfp_movies.utils.Loading
import com.example.kfp_movies.utils.Success
import com.example.kfp_movies.utils.autoCleared
import com.example.kfp_movies.utils.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationsFragment : Fragment(), RecommendationsAdapter.RecommendedItemListener {
    private var binding: RecommendationsFragmentBinding by autoCleared()

    private val viewModel: RecommendationsViewModel by viewModels()

    private lateinit var adapter: RecommendationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecommendationsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val title = getString(R.string.recommendations_fragment_title)
        setToolbarTitle(activity, title)

        adapter = RecommendationsAdapter(this)
        binding.recommendationsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.recommendationsRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.recommendationsRv.smoothScrollToPosition(0)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    Log.d("Success, similar:", it.status.data.toString())
                    if (!it.status.data.isNullOrEmpty()) {
                        binding.progressBar.isVisible = false
                        adapter.setMovies(ArrayList(it.status.data))
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
            viewModel.setId(it)
        }
    }

    override fun onMovieClick(movieId: Int) {
        findNavController().navigate(
            R.id.action_recommendationsFragment2_to_singleRecommendedMovieFragment,
            bundleOf("id" to movieId)
        )
    }

}