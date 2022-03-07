package com.amarydev.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val voteAverage: Double,
    val name: String,
    val posterPath: String,
    val backdropPath: String,
    val firstAirDate: String,
    var isFavorite: Boolean
): Parcelable
