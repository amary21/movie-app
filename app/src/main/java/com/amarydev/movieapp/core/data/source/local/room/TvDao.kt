package com.amarydev.movieapp.core.data.source.local.room

import androidx.room.*
import com.amarydev.movieapp.core.data.source.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {
    @Query("SELECT * FROM tv")
    fun getAllTv(): Flow<List<TvEntity>>

    @Query("SELECT * FROM tv where is_favorite = 1")
    fun getFavoriteTv(): Flow<List<TvEntity>>

    @Query("SELECT EXISTS (SELECT * FROM tv WHERE id=:id AND is_favorite=1)")
    fun isFavorite(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: List<TvEntity>)

    @Update
    suspend fun updateFavoriteTv(tv: TvEntity)
}