package com.example.kfp_movies.ui.all_favorite_movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.FavoriteMovie
import com.example.kfp_movies.databinding.ItemMovieBinding
import com.example.kfp_movies.utils.getRating
import com.example.kfp_movies.utils.reformatDate

class FavoriteMoviesAdapter(private val listener: FavoriteItemListener) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteViewHolder>() {

    private val movies = ArrayList<FavoriteMovie>()

    class FavoriteViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val listener: FavoriteItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var movie: FavoriteMovie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: FavoriteMovie) {
            this.movie = item
            itemBinding.movieTitle.text = item.title
            itemBinding.movieDescription.text = "${item.overview} "
            itemBinding.itemRatingBar.rating = item.vote_average?.let { getRating(it) }!!
            itemBinding.movieReleaseDate.text = item.release_date?.let { reformatDate(it) }
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(R.drawable.glide_placeholder)
                .centerCrop()
                .into(itemBinding.moviePoster)
        }


        override fun onClick(v: View?) {
            movie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setMovies(movies: Collection<FavoriteMovie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun favoriteAt(position: Int) = movies[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.slide_left
            )
        )
    }


    override fun getItemCount() = movies.size

    interface FavoriteItemListener {

        fun onMovieClick(movieId: Int)
    }
}