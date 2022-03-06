package com.amarydev.movieapp.domain.model

import android.os.Parcelable
import com.amarydev.movieapp.core.data.source.local.entity.MovieEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val isFavorite: Boolean
) : Parcelable


fun Movie.mapToEntity() : MovieEntity = MovieEntity(
    id, voteAverage, title, posterPath, backdropPath, releaseDate
)
