package com.amarydev.movieapp.data

import com.amarydev.movieapp.data.model.Detail
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.Tv
import com.amarydev.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getAllTv(): Flow<Resource<List<Tv>>>
    fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>>
    fun getAllFavoriteTv(): Flow<Resource<List<Tv>>>
    fun getDetail(type: String, id: Int) : Flow<Resource<Detail>>
    suspend fun setFavorite(movie: Movie? = null, tv: Tv? = null, state: Boolean)
    fun isFavorite(id: Int, type: String) : Flow<Int>
}