package com.amarydev.movieapp.domain.usecase

import com.amarydev.movieapp.domain.model.Movie
import com.amarydev.movieapp.domain.model.Tv
import com.amarydev.movieapp.domain.repository.IRepository

class Interactor(private val iRepository: IRepository): UseCase {
    override fun getAllMovie() = iRepository.getAllMovie()

    override fun getAllTv() = iRepository.getAllTv()

    override fun getAllFavoriteMovie() = iRepository.getAllFavoriteMovie()

    override fun getAllFavoriteTv() = iRepository.getAllFavoriteTv()

    override fun getDetail(type: String, id: Int) = iRepository.getDetail(type, id)

    override suspend fun setFavorite(movie: Movie?, tv: Tv?, state: Boolean) { iRepository.setFavorite(movie, tv, state) }

    override fun isFavorite(id: Int, type: String) = iRepository.isFavorite(id, type)
}