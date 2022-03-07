package com.amarydev.movieapp.favorite.ui

import androidx.lifecycle.*
import com.amarydev.domain.usecase.UseCase

class FavoriteViewModel(useCase: UseCase) : ViewModel() {

    val getAllFavoriteMovie = useCase.getAllFavoriteMovie().asLiveData()

    val getAllFavoriteTv = useCase.getAllFavoriteTv().asLiveData()
}