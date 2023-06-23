package com.example.kfp_movies.ui.all_favorite_movies

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kfp_movies.R
import com.example.kfp_movies.databinding.FavoriteFragmentBinding
import com.example.kfp_movies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFavoriteMoviesFragment : Fragment(), FavoriteMoviesAdapter.FavoriteItemListener {

    private var binding: FavoriteFragmentBinding by autoCleared()

    private val viewModel: AllFavoriteMoviesViewModel by viewModels()

    private lateinit var adapter: FavoriteMoviesAdapter

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

        adapter = FavoriteMoviesAdapter(this)
        binding.favoriteRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRv.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            binding.favoriteRv.smoothScrollToPosition(0)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.setMovies(it)
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                val item =
                    adapter.favoriteAt(viewHolder.adapterPosition)

                builder.apply {
                    setTitle(getString(R.string.remove_confirmation_title))
                    setMessage(getString(R.string.remove_confirmation_message))
                    setCancelable(false)
                    setPositiveButton(getString(R.string.remove_confirmation_pos)) { _, _ ->
                        viewModel.removeFromFavorites(item)
                        binding.favoriteRv.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    setNegativeButton(getString(R.string.confirmation_dialog_cancel)) { _, _ ->
                        binding.favoriteRv.adapter!!.notifyItemChanged(viewHolder.adapterPosition)
                    }


                }.show()


            }

            // Implement other necessary methods for item touch handling if needed
        })

        itemTouchHelper.attachToRecyclerView(binding.favoriteRv)
    }


    override fun onMovieClick(movieId: Int) {
        findNavController().navigate(
            R.id.action_allFavoriteMoviesFragment_to_singleMovieFragment,
            bundleOf("id" to movieId)
        )
    }
}