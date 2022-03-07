package com.amarydev.movieapp.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.domain.repository.IRepository
import com.amarydev.movieapp.domain.usecase.UseCase

class TvShowViewModel(useCase: UseCase) : ViewModel() {
    val tv = useCase.getAllTv().asLiveData()
}