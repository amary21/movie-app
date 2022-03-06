package com.amarydev.movieapp.data.source.local

import com.amarydev.movieapp.data.source.local.entity.MovieEntity
import com.amarydev.movieapp.data.source.local.entity.TvEntity
import com.amarydev.movieapp.data.source.local.room.MovieDao
import com.amarydev.movieapp.data.source.local.room.TvDao

class LocalDataSource(private val movieDao: MovieDao, private val tvDao: TvDao) {

    fun getAllMovies() = movieDao.getAllMovies()

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    fun isFavoriteMovie(id: Int) = movieDao.isFavorite(id)

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

    fun getAllTv() = tvDao.getAllTv()

    fun getFavoriteTv() = tvDao.getFavoriteTv()

    fun isFavoriteTv(id: Int) = tvDao.isFavorite(id)

    suspend fun insertTv(tv: List<TvEntity>) = tvDao.insertTv(tv)

    suspend fun setFavoriteTv(tv: TvEntity, newState: Boolean){
        tv.isFavorite = newState
        tvDao.updateFavoriteTv(tv)
    }
}