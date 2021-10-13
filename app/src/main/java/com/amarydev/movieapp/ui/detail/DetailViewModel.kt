package com.amarydev.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.data.IRepository

class DetailViewModel(private val iRepository: IRepository) : ViewModel() {

    fun movie(id: Int) = iRepository.getDetailMovie(id).asLiveData()
}