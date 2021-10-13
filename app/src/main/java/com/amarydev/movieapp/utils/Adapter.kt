package com.amarydev.movieapp.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amarydev.movieapp.R
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.databinding.ItemMovieBinding
import com.amarydev.movieapp.utils.Constant.COVER_IMAGE
import com.bumptech.glide.Glide

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var movies = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(movies: List<Movie>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val data = movies[position]
        holder.bind(data)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(movie: Movie){
            with(binding){
                Glide.with(itemView.context)
                    .load(COVER_IMAGE + movie.posterPath)
                    .into(ivItemImage)

                tvItemTitle.text = movie.title
                tvItemRating.text = movie.voteAverage.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(movies[adapterPosition])
            }
        }
    }

}