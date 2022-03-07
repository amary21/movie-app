package com.amarydev.domain.model

import android.os.Parcelable
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
