package com.amarydev.movieapp.di

import android.content.Context
import com.amarydev.movieapp.data.IRepository
import com.amarydev.movieapp.data.Repository
import com.amarydev.movieapp.data.source.local.LocalDataSource
import com.amarydev.movieapp.data.source.local.room.MovieDatabase
import com.amarydev.movieapp.data.source.remote.RemoteDataSource
import com.amarydev.movieapp.data.source.remote.network.ApiConfig

object Injection {

    fun provideRepository(context: Context) : IRepository{
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.movieDao())

        return Repository.getInstance(remoteDataSource, localDataSource)
    }
}