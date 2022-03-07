package com.amarydev.core.data.source.remote.response

import com.amarydev.core.data.source.local.entity.TvEntity
import com.google.gson.annotations.SerializedName

data class ResultTvResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("first_air_date") val firstAirDate: String
)

fun ResultTvResponse.mapToEntity() : TvEntity = TvEntity(
    id, voteAverage, name, posterPath, backdropPath, firstAirDate
)

fun List<ResultTvResponse>.mapToEntity() : List<TvEntity> = map { it.mapToEntity() }
