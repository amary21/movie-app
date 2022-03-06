package com.amarydev.movieapp.core.data.source.remote

import com.amarydev.movieapp.core.data.source.remote.network.ApiService
import com.amarydev.movieapp.core.data.source.remote.response.DetailResponse
import com.amarydev.movieapp.core.data.source.remote.response.ResultResponse
import com.amarydev.movieapp.core.data.source.remote.response.ResultTvResponse
import com.amarydev.movieapp.core.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService){

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

    suspend fun getAllTv(): Flow<ApiResponse<List<ResultTvResponse>>>{
        return flow {
            try {
                val response = apiService.getTvOnAir()
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

    suspend fun getDetail(type: String, id : Int) : Flow<ApiResponse<DetailResponse>>{
        return flow {
            try {
                val response = apiService.getDetail(type, id)
                if (response != null){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.message.toString()))
            } finally {

            }
        }.flowOn(Dispatchers.IO)
    }
}