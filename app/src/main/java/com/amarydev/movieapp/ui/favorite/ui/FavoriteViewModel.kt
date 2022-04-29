package com.amarydev.movieapp.ui.favorite.ui

import androidx.lifecycle.*
import com.amarydev.movieapp.domain.usecase.UseCase

class FavoriteViewModel(useCase: UseCase) : ViewModel() {

    val getAllFavoriteMovie = useCase.getAllFavoriteMovie().asLiveData()

    val getAllFavoriteTv = useCase.getAllFavoriteTv().asLiveData()
}