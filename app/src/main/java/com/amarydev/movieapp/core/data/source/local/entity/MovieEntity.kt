package com.amarydev.movieapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amarydev.movieapp.domain.model.Movie


@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "vote_average") var voteAverage: Double,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "poster_path") var posterPath: String,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String,
    @ColumnInfo(name = "release_date") var releaseDate: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
)

fun MovieEntity.mapToModel() : Movie = Movie(
    id, voteAverage, title, posterPath, backdropPath, releaseDate, isFavorite
)

fun List<MovieEntity>.mapToModel(): List<Movie> = map { it.mapToModel() }
