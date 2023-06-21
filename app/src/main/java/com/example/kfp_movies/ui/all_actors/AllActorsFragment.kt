package com.example.kfp_movies.ui.all_actors

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kfp_movies.R
import com.example.kfp_movies.databinding.ActorsFragmentBinding
import com.example.kfp_movies.utils.Loading
import com.example.kfp_movies.utils.Success
import com.example.kfp_movies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllActorsFragment : Fragment(), ActorsAdapter.ActorItemListener {

    private var binding: ActorsFragmentBinding by autoCleared()

    private val viewModel: AllActorsViewModel by viewModels()

    private lateinit var adapter: ActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActorsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActorsAdapter(this)
        binding.actorsRv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.actorsRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.actorsRv.smoothScrollToPosition(0)
        }

        viewModel.actors.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if (!it.status.data.isNullOrEmpty()) {
                        binding.progressBar.isVisible = false
                        adapter.setActors(ArrayList(it.status.data))

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


            viewModel.setId(it)
        }

    }


    override fun onActorClick(actorId: Int) {
        findNavController().navigate(
            R.id.action_allActorsFragment_to_singleActorFragment,
            bundleOf("id" to actorId)
        )
    }
}