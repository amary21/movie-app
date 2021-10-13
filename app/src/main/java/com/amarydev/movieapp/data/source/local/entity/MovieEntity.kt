package com.amarydev.movieapp.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amarydev.movieapp.data.model.Movie


@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String
)

fun MovieEntity.mapToModel() : Movie = Movie(
    id, voteAverage, title, posterPath, backdropPath, releaseDate
)

fun List<MovieEntity>.mapToModel(): List<Movie> = map { it.mapToModel() }
