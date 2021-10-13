package com.amarydev.movieapp.data.source.remote.response

import com.amarydev.movieapp.data.model.Movie
import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String
)


fun ResultResponse.mapToModel() : Movie = Movie(
    id,
    voteAverage,
    title,
    posterPath,
    backdropPath,
    releaseDate
)

fun List<ResultResponse>.mapToModel() : List<Movie> = map { it.mapToModel() }