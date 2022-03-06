package com.amarydev.movieapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amarydev.movieapp.core.data.source.local.entity.MovieEntity
import com.amarydev.movieapp.core.data.source.local.entity.TvEntity


@Database(entities = [MovieEntity::class, TvEntity::class], version = 2, exportSchema = false)
abstract class ConfigDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
}