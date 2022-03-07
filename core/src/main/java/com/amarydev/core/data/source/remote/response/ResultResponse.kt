package com.amarydev.core.data.source.remote.response

import com.amarydev.core.data.source.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String
)


fun ResultResponse.mapToEntity() : MovieEntity = MovieEntity(
    id,
    voteAverage,
    title,
    posterPath,
    backdropPath,
    releaseDate
)

fun List<ResultResponse>.mapToEntity() : List<MovieEntity> = map { it.mapToEntity() }