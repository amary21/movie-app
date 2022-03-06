package com.amarydev.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.domain.repository.IRepository

class MovieViewModel(iRepository: IRepository) : ViewModel() {

    val movies = iRepository.getAllMovie().asLiveData()
}