package com.amarydev.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.data.IRepository

class HomeViewModel(iRepository: IRepository) : ViewModel() {

    val movies = iRepository.getAllMovie().asLiveData()
}