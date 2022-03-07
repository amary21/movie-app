package com.amarydev.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amarydev.domain.model.Tv

@Entity(tableName = "tv")
data class TvEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
)

fun TvEntity.mapToModel(): Tv = Tv(id, voteAverage, name, posterPath, backdropPath, firstAirDate, isFavorite)

fun List<TvEntity>.mapToModel(): List<Tv> = map { it.mapToModel() }


fun Tv.mapToEntity(): TvEntity = TvEntity(id, voteAverage, name, posterPath, backdropPath, firstAirDate, isFavorite)
