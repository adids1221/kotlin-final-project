package com.example.kfp_movies.ui.all_recommendation_movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.RecommendedMovie
import com.example.kfp_movies.databinding.ItemMovieBinding
import com.example.kfp_movies.utils.getRating

class RecommendationsAdapter(private val listener: RecommendedItemListener) :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendedViewHolder>() {

    private val movies = ArrayList<RecommendedMovie>()

    class RecommendedViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val listener: RecommendedItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var movie: RecommendedMovie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SuspiciousIndentation")
        fun bind(item: RecommendedMovie) {
            this.movie = item
            itemBinding.movieTitle.text = item.title
            itemBinding.movieDescription.text = "${item.overview} "
            itemBinding.itemRatingBar.rating = item.vote_average?.let { getRating(it) }!!
            itemBinding.movieReleaseDate.text = item.release_date
              Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .placeholder(R.drawable.glide_placeholder)
                .centerCrop()
                .into(itemBinding.moviePoster)
        }

        override fun onClick(v: View?) {
            movie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setMovies(movies: Collection<RecommendedMovie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) =
        holder.bind(movies[position])


    override fun getItemCount() = movies.size

    interface RecommendedItemListener {
        fun onMovieClick(movieId: Int)
    }
}