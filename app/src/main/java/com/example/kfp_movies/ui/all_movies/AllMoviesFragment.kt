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
import com.example.kfp_movies.databinding.MoviesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.finalkotlinproject.utils.Error
import il.co.syntax.finalkotlinproject.utils.Loading
import il.co.syntax.finalkotlinproject.utils.Success
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class AllMoviesFragment : Fragment(), MoviesAdapter.CharacterItemListener {

    private var binding : MoviesFragmentBinding by autoCleared()

    private val viewModel: AllMoviesViewModel by viewModels()

    private  lateinit var  adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoviesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesAdapter(this)
        binding.moviesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRv.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) {
            when(it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if(!it.status.data.isNullOrEmpty()) {
                        binding.progressBar.isVisible = false
                        adapter.setMovie(ArrayList(it.status.data))
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(),it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCharacterClick(characterId: Int) {
//        findNavController().navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment,
//            bundleOf("id" to characterId)
//        )
    }
}