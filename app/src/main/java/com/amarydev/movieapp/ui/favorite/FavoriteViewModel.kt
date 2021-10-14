package com.amarydev.movieapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.data.IRepository

class FavoriteViewModel(iRepository: IRepository) : ViewModel() {
    val movies = iRepository.getAllFavoriteMovie().asLiveData()
}