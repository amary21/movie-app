package com.amarydev.movieapp.data.model

import android.os.Parcelable
import com.amarydev.movieapp.data.source.local.entity.MovieEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String
) : Parcelable


fun Movie.mapToEntity() : MovieEntity = MovieEntity(
    id, voteAverage, title, posterPath, backdropPath, releaseDate
)
