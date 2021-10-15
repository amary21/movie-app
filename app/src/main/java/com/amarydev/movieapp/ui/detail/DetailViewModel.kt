package com.amarydev.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amarydev.movieapp.data.IRepository
import com.amarydev.movieapp.data.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val iRepository: IRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun movie(id: Int) = iRepository.getDetailMovie(id).asLiveData()

    fun isFavorite(id: Int) = iRepository.isFavorite(id).asLiveData()

    fun setFavorite(movie: Movie, state: Boolean) = viewModelScope.launch(coroutineDispatcher){
        iRepository.setFavorite(movie, state)
    }
}