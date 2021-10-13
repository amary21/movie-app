package com.amarydev.movieapp.data.source.local.room

import androidx.room.*
import com.amarydev.movieapp.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavorite(movieEntity: MovieEntity)

    @Delete
    suspend fun unsetFavorite(movieEntity: MovieEntity)
}