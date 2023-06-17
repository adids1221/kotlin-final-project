package com.example.kfp_movies.ui.all_movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.databinding.ItemCharacterBinding

class MoviesAdapter(private val listener: CharacterItemListener) :
    RecyclerView.Adapter<MoviesAdapter.CharacterViewHolder>() {

    private val movies = ArrayList<Movie>()

    class CharacterViewHolder(
        private val itemBinding: ItemCharacterBinding,
        private val listener: CharacterItemListener
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
//            listener.onCharacterClick(character.id)
        }
    }

    fun setMovie(movies: Collection<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(movies[position])


    override fun getItemCount() = movies.size

    interface CharacterItemListener {
        fun onCharacterClick(characterId: Int)
    }
}