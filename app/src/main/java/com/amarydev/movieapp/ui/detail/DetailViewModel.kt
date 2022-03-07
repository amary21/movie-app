package com.amarydev.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amarydev.domain.model.Movie
import com.amarydev.domain.model.Tv
import com.amarydev.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val useCase: UseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun detail(type:String, id: Int) = useCase.getDetail(type, id).asLiveData()

    fun isFavorite(id: Int, type: String) = useCase.isFavorite(id, type).asLiveData()

    fun setFavorite(movie: Movie? = null, tv: Tv? = null, state: Boolean) = viewModelScope.launch(coroutineDispatcher){
        useCase.setFavorite(movie, tv, state)
    }
}