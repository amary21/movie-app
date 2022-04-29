package com.amarydev.movieapp.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amarydev.movieapp.R
import com.amarydev.movieapp.domain.model.Movie
import com.amarydev.movieapp.domain.model.Tv
import com.amarydev.movieapp.databinding.ItemListBinding
import com.amarydev.movieapp.core.utils.Constant.COVER_IMAGE
import com.bumptech.glide.Glide

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var movies = ArrayList<Movie>()
    private var tv = ArrayList<Tv>()
    var onItemClickMovie: ((Movie) -> Unit)? = null
    var onItemClickTv: ((Tv) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataMovie(movies: List<Movie>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataTv(tv: List<Tv>){
        this.tv.clear()
        this.tv.addAll(tv)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!movies.isNullOrEmpty()) {
            val data = movies[position]
            holder.bindMovie(data)
        } else {
            val data = tv[position]
            holder.bindTv(data)
        }
    }

    override fun getItemCount() = if (!movies.isNullOrEmpty()) movies.size else tv.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemListBinding.bind(itemView)
        fun bindMovie(movie: Movie){
            with(binding){
                Glide.with(itemView.context)
                    .load(COVER_IMAGE + movie.posterPath)
                    .into(ivItemImage)

                tvItemTitle.text = movie.title
                tvItemRating.text = movie.voteAverage.toString()
            }
        }

        fun bindTv(tv: Tv) {
            with(binding){
                Glide.with(itemView.context)
                    .load(COVER_IMAGE + tv.posterPath)
                    .into(ivItemImage)

                tvItemTitle.text = tv.name
                tvItemRating.text = tv.voteAverage.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                if (!movies.isNullOrEmpty()){
                    onItemClickMovie?.invoke(movies[adapterPosition])
                } else {
                    onItemClickTv?.invoke(tv[adapterPosition])
                }
            }
        }
    }

}