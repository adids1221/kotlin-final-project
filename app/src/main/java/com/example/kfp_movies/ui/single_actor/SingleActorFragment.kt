package com.example.kfp_movies.ui.single_actor

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
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.Actor
import com.example.kfp_movies.databinding.SingleActorFragmentBinding
import com.example.kfp_movies.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleActorFragment : Fragment() {


    private var binding: SingleActorFragmentBinding by autoCleared()

    private val viewModel: SingleActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingleActorFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.actor.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    if (it.status.data != null) {
                        binding.progressBar.isVisible = false
                        updateActor(it.status.data!!)
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        arguments?.getInt("id")?.let {
            Log.d(it.toString(), it.toString())


            viewModel.setId(it)
        }


    }

    private fun updateActor(actor: Actor) {

        binding.actorName.text = actor.name
        binding.actorBiography.text = actor.biography
        binding.actorBirthday.text = actor.birthday?.let { reformatDate(it) }
        binding.actorRatingBar.rating = actor.popularity?.let { getRating(it) }!!
        binding.placeOfBirth.text = actor.place_of_birth
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w500${actor.profile_path}")
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.actorProfile)
    }
}
