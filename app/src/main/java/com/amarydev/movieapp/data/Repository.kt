package com.amarydev.movieapp.data

import android.content.Context
import com.amarydev.movieapp.data.model.Detail
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.mapToEntity
import com.amarydev.movieapp.data.source.local.LocalDataSource
import com.amarydev.movieapp.data.source.local.entity.mapToModel
import com.amarydev.movieapp.data.source.remote.RemoteDataSource
import com.amarydev.movieapp.data.source.remote.response.ResultResponse
import com.amarydev.movieapp.data.source.remote.response.mapToEntity
import com.amarydev.movieapp.data.source.remote.response.mapToModel
import com.amarydev.movieapp.utils.ApiResponse
import com.amarydev.movieapp.utils.ConnectionCheck
import com.amarydev.movieapp.utils.NetworkBoundResource
import com.amarydev.movieapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val context: Context ) : IRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultResponse>>() {
            override fun loadFromDB() =
                localDataSource.getAllMovies().map {
                    it.mapToModel()
                }

            override fun shouldFetch(data: List<Movie>?) =
                ConnectionCheck.isNetworkAvailable(context)

            override suspend fun createCall() =
                remoteDataSource.getAllMovies()


            override suspend fun saveCallResult(data: List<ResultResponse>) {
                localDataSource.insertMovie(data.mapToEntity())
            }

        }.asFlow()

    override fun getAllFavoriteMovie(): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = localDataSource.getFavoriteMovie().first()
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
        }
    }

    override suspend fun setFavorite(movie: Movie, state: Boolean) =
        localDataSource.setFavoriteMovie(movie.mapToEntity(), state)

    override fun isFavorite(id: Int): Flow<Int> {
        return localDataSource.isFavorite(id)
    }


}