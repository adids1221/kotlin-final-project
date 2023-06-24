package com.example.kfp_movies.ui.all_similar_movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.SimilarMovie
import com.example.kfp_movies.databinding.ItemMovieBinding
import com.example.kfp_movies.utils.getRating
import com.example.kfp_movies.utils.reformatDate

class SimilarAdapter(private val listener: SimilarItemListener) :
    RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {

    private val movies = ArrayList<SimilarMovie>()

    class SimilarViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val listener: SimilarItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var movie: SimilarMovie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: SimilarMovie) {
            this.movie = item
            itemBinding.movieTitle.text = item.title
            itemBinding.movieDescription.text = "${item.overview} "
            itemBinding.itemRatingBar.rating = item.vote_average?.let { getRating(it) }!!
            itemBinding.movieReleaseDate.text = item.release_date?.let { reformatDate(it) }
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(R.drawable.glide_placeholder).centerCrop()
                .into(itemBinding.moviePoster)
        }

        override fun onClick(v: View?) {
            movie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setMovies(movies: Collection<SimilarMovie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.slide_left
            )
        )
    }


    override fun getItemCount() = movies.size

    interface SimilarItemListener {
        fun onMovieClick(movieId: Int)
    }
}