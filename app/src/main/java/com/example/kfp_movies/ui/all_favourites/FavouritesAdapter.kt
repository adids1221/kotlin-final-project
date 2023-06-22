import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.data.models.Actor
import com.example.kfp_movies.data.models.FavouriteMovie
import com.example.kfp_movies.data.models.Movie
import com.example.kfp_movies.databinding.ItemActorBinding
import com.example.kfp_movies.databinding.ItemFavouriteBinding
import com.example.kfp_movies.utils.getRating

class FavouritesAdapter(private val listener: FavouriteItemListener) :
    RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {


    private val favourites = ArrayList<FavouriteMovie>()

    class FavouriteViewHolder(
        private val itemBinding: ItemFavouriteBinding,
        private val listener: FavouriteItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {


        private lateinit var favouriteMovie: FavouriteMovie

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: FavouriteMovie) {

            this.favouriteMovie = item
            itemBinding.movieTitle.text = item.title
            itemBinding.movieDescription.text = "${item.overview} "
            itemBinding.itemRatingBar.rating = item.vote_average?.let { getRating(it) }!!
            itemBinding.movieReleaseDate.text = item.release_date
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${item.poster_path}").centerCrop()
                .into(itemBinding.moviePoster)

        }

        override fun onClick(v: View?) {
            favouriteMovie.id?.let { listener.onMovieClick(it) }
        }
    }

    fun setFavourites(favourites: Collection<FavouriteMovie>) {
        this.favourites.clear()
        this.favourites.addAll(favourites)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding =
            ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) =
        holder.bind(favourites[position])


    override fun getItemCount() = favourites.size


    interface FavouriteItemListener {

        fun onMovieClick(movieId: Int)
    }
}