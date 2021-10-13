package com.amarydev.movieapp.data.source.local

import com.amarydev.movieapp.data.source.local.entity.MovieEntity
import com.amarydev.movieapp.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
    }

    fun getAllMovies() = movieDao.getAllMovies()

    suspend fun setFavorite(movieEntity: MovieEntity) = movieDao.setFavorite(movieEntity)

    suspend fun unsetFavorite(movieEntity: MovieEntity) = movieDao.unsetFavorite(movieEntity)
}