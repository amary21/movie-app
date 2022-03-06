package com.amarydev.movieapp.data

import android.content.Context
import com.amarydev.movieapp.data.model.Detail
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.Tv
import com.amarydev.movieapp.data.model.mapToEntity
import com.amarydev.movieapp.data.source.local.LocalDataSource
import com.amarydev.movieapp.data.source.local.entity.mapToModel
import com.amarydev.movieapp.data.source.remote.RemoteDataSource
import com.amarydev.movieapp.data.source.remote.response.ResultResponse
import com.amarydev.movieapp.data.source.remote.response.ResultTvResponse
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
                data == null || data.isEmpty()

            override suspend fun createCall() =
                remoteDataSource.getAllMovies()


            override suspend fun saveCallResult(data: List<ResultResponse>) {
                localDataSource.insertMovie(data.mapToEntity())
            }

        }.asFlow()

    override fun getAllTv(): Flow<Resource<List<Tv>>> =
        object : NetworkBoundResource<List<Tv>, List<ResultTvResponse>>(){
            override fun loadFromDB() = localDataSource.getAllTv().map {
                it.mapToModel()
            }

            override fun shouldFetch(data: List<Tv>?) =
                data == null || data.isEmpty()

            override suspend fun createCall() =
                remoteDataSource.getAllTv()

            override suspend fun saveCallResult(data: List<ResultTvResponse>) {
                localDataSource.insertTv(data.mapToEntity())
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

    override fun getAllFavoriteTv(): Flow<Resource<List<Tv>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = localDataSource.getFavoriteTv().first()
                if (result.isNotEmpty()){
                    emit(Resource.Success(result.mapToModel()))
                } else {
                    emit(Resource.Error<List<Tv>>("data empty"))
                }
            } catch (e: Exception){
                emit(Resource.Error<List<Tv>>(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetail(type:String, id: Int): Flow<Resource<Detail>> {
        return flow {
            emit(Resource.Loading())
            when(val result = remoteDataSource.getDetail(type, id).first()){
                is ApiResponse.Success -> emit(Resource.Success(result.data.mapToModel()))
                is ApiResponse.Error -> emit(Resource.Error<Detail>(result.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Error<Detail>("data empty"))
            }
        }
    }

    override suspend fun setFavorite(movie: Movie?, tv: Tv?, state: Boolean){
        if (movie != null){
            localDataSource.setFavoriteMovie(movie.mapToEntity(), state)
        } else if (tv != null){
            localDataSource.setFavoriteTv(tv.mapToEntity(), state)
        }
    }


    override fun isFavorite(id: Int, type: String): Flow<Int> {
        return if (type == "movie"){
            localDataSource.isFavoriteMovie(id)
        } else {
            localDataSource.isFavoriteTv(id)
        }
    }


}