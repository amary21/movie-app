package com.amarydev.movieapp.data.source.remote.network

import com.amarydev.movieapp.data.source.remote.response.DetailResponse
import com.amarydev.movieapp.data.source.remote.response.MovieResponse
import com.amarydev.movieapp.data.source.remote.response.TvResponse
import com.amarydev.movieapp.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieRelease(
        @Query("api_key") api_key: String? = API_KEY,
        @Query("language") language: String? = "en-US"
    ): MovieResponse

    @GET("tv/on_the_air")
    suspend fun getTvOnAir(
        @Query("api_key") api_key: String? = API_KEY,
        @Query("language") language: String? = "en-US"
    ): TvResponse

    @GET("{type}/{id}")
    suspend fun getDetail(
        @Path("type") type: String,
        @Path("id") id: Int,
        @Query("api_key") api_key: String? = API_KEY,
        @Query("language") language: String? = "en-US"
    ): DetailResponse?
}