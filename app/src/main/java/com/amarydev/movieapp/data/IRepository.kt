package com.amarydev.movieapp.data

import com.amarydev.movieapp.data.model.Detail
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Int) : Flow<Resource<Detail>>
    fun setFavorite(movie: Movie, state: Boolean)
    fun isFavorite(id: Int) : Flow<Int>
}