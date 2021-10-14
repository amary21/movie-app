package com.amarydev.movieapp.data.source.remote

import android.util.Log
import com.amarydev.movieapp.utils.ApiResponse
import com.amarydev.movieapp.data.source.remote.network.ApiService
import com.amarydev.movieapp.data.source.remote.response.DetailResponse
import com.amarydev.movieapp.data.source.remote.response.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(service)
            }
    }

    suspend fun getAllMovies(): Flow<ApiResponse<List<ResultResponse>>> {
        return flow {
            try {
                val response = apiService.getMovieRelease()
                val data = response.results
                if (data.isNotEmpty())
                    emit(ApiResponse.Success(data))
                else
                    emit(ApiResponse.Empty)
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(id : Int) : Flow<ApiResponse<DetailResponse>>{
        return flow {
            try {
                val response = apiService.getDetailMovie(movie_id = id)
                if (response != null){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
//                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}