package com.example.kfp_movies.ui.all_movies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.data.repository.MovieRepository
import com.example.kfp_movies.databinding.ItemMovieBinding
import com.example.kfp_movies.ui.all_favourites.AllFavouritesViewModel
import com.example.kfp_movies.utils.getRating

class MoviesAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(), Filterable {

    private val movies = ArrayList<Movie>()
    private var moviesFilterList = ArrayList<Movie>()



    init {
        moviesFilterList = movies
    }

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
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
                .placeholder(R.drawable.glide_placeholder)
                .centerCrop()
                .into(itemBinding.moviePoster)
        }

        override fun onClick(v: View?) {
            movie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setMovies(movies: Collection<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        moviesFilterList.clear()
        moviesFilterList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(moviesFilterList[position])

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterPattern = constraint.toString()
                if (filterPattern.isEmpty()) {
                    moviesFilterList = movies
                } else {
                    val resultList = ArrayList<Movie>()
                    for (movie in movies) {
                        if (movie.title?.lowercase()?.contains(filterPattern) == true
                        ) {
                            resultList.add(movie)
                        }
                    }
                    moviesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFilterList = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount() = moviesFilterList.size

    interface MovieItemListener {

        fun onMovieClick(movieId: Int)
    }

}
