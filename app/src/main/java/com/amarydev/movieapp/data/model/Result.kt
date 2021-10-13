package com.amarydev.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id") val id: Int,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String
)
