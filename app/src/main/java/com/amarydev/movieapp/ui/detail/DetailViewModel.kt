package com.amarydev.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.data.IRepository
import com.amarydev.movieapp.data.model.Movie

class DetailViewModel(private val iRepository: IRepository) : ViewModel() {

    fun movie(id: Int) = iRepository.getDetailMovie(id).asLiveData()

    fun isFavorite(id: Int) = iRepository.isFavorite(id).asLiveData()

    fun setFavorite(movie: Movie, state: Boolean) = iRepository.setFavorite(movie, state)
}