package com.example.kfp_movies.ui.single_movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.Review
import com.example.kfp_movies.databinding.ItemReviewBinding
import com.example.kfp_movies.utils.getRating

class SingleMovieReviewsAdapter(private val listener: ReviewItemListener) :
    RecyclerView.Adapter<SingleMovieReviewsAdapter.ReviewMovieViewHolder>() {

    private val reviews = ArrayList<Review>()

    class ReviewMovieViewHolder(
        private val itemBinding: ItemReviewBinding,
        private val listener: ReviewItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var review: Review

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Review) {

            this.review = item
            itemBinding.authorName.text = item.author
            itemBinding.reviewContent.text = item.content
            itemBinding.itemRatingBar.rating = item.author_details?.rating?.let { getRating(it) }!!
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w200${item.author_details.avatar_path}")
                .placeholder(R.drawable.glide_placeholder)
                .centerCrop()
                .into(itemBinding.authorAvatar)
        }

        override fun onClick(v: View?) {
            review.id?.let { listener.onReviewClick(it) }
        }
    }

    fun setReviews(reviews: Collection<Review>) {
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMovieViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewMovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ReviewMovieViewHolder, position: Int) =
        holder.bind(reviews[position])


    override fun getItemCount() = reviews.size

    interface ReviewItemListener {
        fun onReviewClick(reviewId: String)
    }
}