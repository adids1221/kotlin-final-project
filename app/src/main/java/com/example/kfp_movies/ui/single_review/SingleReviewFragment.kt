package com.example.kfp_movies.ui.single_review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.Review
import com.example.kfp_movies.databinding.SingleReviewFragmentBinding
import com.example.kfp_movies.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleReviewFragment : Fragment() {


    private var binding: SingleReviewFragmentBinding by autoCleared()

    private val viewModel: SingleReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingleReviewFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val title = getString(R.string.review_fragment_title)
        setToolbarTitle(activity, title)

        viewModel.review.observe(viewLifecycleOwner) { updateReview(it) }

        arguments?.getString("id")?.let {

            viewModel.setId(it)
        }


    }


    private fun updateReview(review: Review) {
        binding.authorName.text = review.author
        binding.reviewContent.text = review.content
        binding.itemRatingBar.rating = review.author_details?.rating?.let { getRating(it) }!!
        binding.reviewDate.text = review.created_at?.let { reformatDate(it) }
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w200${review.author_details.avatar_path}")
            .placeholder(R.drawable.glide_placeholder)
            .centerCrop()
            .into(binding.authorAvatar)


    }
}
