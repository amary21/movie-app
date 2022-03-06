package com.amarydev.movieapp.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.domain.repository.IRepository

class TvShowViewModel(iRepository: IRepository) : ViewModel() {
    val tv = iRepository.getAllTv().asLiveData()
}