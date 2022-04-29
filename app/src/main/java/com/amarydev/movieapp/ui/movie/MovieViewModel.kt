package com.amarydev.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amarydev.movieapp.domain.repository.IRepository
import com.amarydev.movieapp.domain.usecase.UseCase

class MovieViewModel(useCase: UseCase) : ViewModel() {

    val movies = useCase.getAllMovie().asLiveData()
}