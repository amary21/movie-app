package com.amarydev.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amarydev.movieapp.data.IRepository
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.Tv
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val iRepository: IRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun detail(type:String, id: Int) = iRepository.getDetail(type, id).asLiveData()

    fun isFavorite(id: Int, type: String) = iRepository.isFavorite(id, type).asLiveData()

    fun setFavorite(movie: Movie? = null, tv: Tv? = null, state: Boolean) = viewModelScope.launch(coroutineDispatcher){
        iRepository.setFavorite(movie, tv, state)
    }
}