package com.example.kfp_movies.ui.all_actors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kfp_movies.R
import com.example.kfp_movies.data.models.Actor
import com.example.kfp_movies.databinding.ItemActorBinding

class ActorsAdapter(private val listener: ActorItemListener) :
    RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {


    private val actors = ArrayList<Actor>()

    class ActorViewHolder(
        private val itemBinding: ItemActorBinding,
        private val listener: ActorItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {


        private lateinit var actor: Actor

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Actor) {

            this.actor = item
            itemBinding.actorName.text = item.name
            itemBinding.movieCharacter.text = item.character
            Glide.with(itemBinding.root)
                .load("https://image.tmdb.org/t/p/w500${item.profile_path}")
                .placeholder(R.drawable.glide_placeholder)
                .centerCrop()
                .into(itemBinding.profileImage)

            itemBinding.cardView.setOnClickListener {
                actor.id?.let { listener.onActorClick(it) }
            }

        }

        override fun onClick(v: View?) {
            actor.id?.let { listener.onActorClick(it) }
        }
    }

    fun setActors(actors: Collection<Actor>) {
        this.actors.clear()
        this.actors.addAll(actors)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding =
            ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) =
        holder.bind(actors[position])


    override fun getItemCount() = actors.size


    interface ActorItemListener {

        fun onActorClick(actorId: Int)
    }
}