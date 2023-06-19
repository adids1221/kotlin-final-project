package com.example.kfp_movies.ui.all_movies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.databinding.ItemMovieBinding
import com.example.kfp_movies.utils.getRating

class MoviesAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movies = ArrayList<Movie>()

    class MovieViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val listener: MovieItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var movie: Movie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Movie) {

            this.movie = item
            itemBinding.movieTitle.text = item.title
            itemBinding.movieDescription.text = "${item.overview} "
            itemBinding.itemRatingBar.rating = item.vote_average?.let { getRating(it) }!!
            itemBinding.movieReleaseDate.text = item.release_date
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}").centerCrop()
                .into(itemBinding.moviePoster)
            itemBinding.favStar.setOnCheckedChangeListener { _, isChecked ->
                Log.d(item.title, "This movie ${item.title} has been marked!")
            }
        }

        override fun onClick(v: View?) {
            movie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setMovies(movies: Collection<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movies[position])


    override fun getItemCount() = movies.size

    interface MovieItemListener {

        fun onMovieClick(movieId: Int)
    }
}