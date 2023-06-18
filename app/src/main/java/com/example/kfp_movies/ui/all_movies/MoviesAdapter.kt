package com.example.kfp_movies.ui.all_movies
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.databinding.ItemMovieBinding

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
            itemBinding.name.text = item.title
            itemBinding.speciesAndStatus.text = "${item.overview} "
            Glide.with(itemBinding.root)
                .load("https://api.themoviedb.org/3/movie${item.backdropPath}")
                .into(itemBinding.image)
        }

        override fun onClick(v: View?) {
            listener.onMovieClick(movie.id)
        }
    }

    fun setMovie(movies: Collection<Movie>) {
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