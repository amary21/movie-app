package com.amarydev.movieapp.data.model

import android.os.Parcelable
import com.amarydev.movieapp.data.source.local.entity.TvEntity
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

fun Tv.mapToEntity(): TvEntity = TvEntity(id, voteAverage, name, posterPath, backdropPath, firstAirDate)
