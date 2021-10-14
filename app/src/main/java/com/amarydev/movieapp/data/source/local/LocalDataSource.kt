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

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    fun isFavorite(id: Int) = movieDao.isFavorite(id)

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}