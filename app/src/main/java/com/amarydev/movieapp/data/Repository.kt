package com.amarydev.movieapp.data

import com.amarydev.movieapp.data.model.Detail
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.mapToEntity
import com.amarydev.movieapp.data.source.local.LocalDataSource
import com.amarydev.movieapp.data.source.local.entity.mapToModel
import com.amarydev.movieapp.data.source.remote.RemoteDataSource
import com.amarydev.movieapp.data.source.remote.response.mapToModel
import com.amarydev.movieapp.utils.ApiResponse
import com.amarydev.movieapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource) : IRepository {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource, localDataSource)
            }
    }

    override fun getAllMovie(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            when(val result = remoteDataSource.getAllMovies().first()){
                is ApiResponse.Success -> emit(Resource.Success(result.data.mapToModel()))
                is ApiResponse.Error -> emit(Resource.Error<List<Movie>>(result.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Error<List<Movie>>("data empty"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = localDataSource.getAllMovies().first()
                if (result.isNotEmpty()){
                    emit(Resource.Success(result.mapToModel()))
                } else {
                    emit(Resource.Error<List<Movie>>("data empty"))
                }
            } catch (e: Exception){
                emit(Resource.Error<List<Movie>>(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetailMovie(id: Int): Flow<Resource<Detail>> {
        return flow {
            emit(Resource.Loading())
            when(val result = remoteDataSource.getDetailMovie(id).first()){
                is ApiResponse.Success -> emit(Resource.Success(result.data.mapToModel()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(result.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Error<Detail>("data empty"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun setFavorite(movie: Movie): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun unsetFavorite(movie: Movie): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }


}