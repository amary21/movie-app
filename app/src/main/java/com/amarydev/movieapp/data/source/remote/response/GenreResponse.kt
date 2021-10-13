package com.amarydev.movieapp.data.source.remote.response

import com.amarydev.movieapp.data.model.Genre
import com.amarydev.movieapp.data.model.Movie
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("name") val name: String
)

fun GenreResponse.mapToModel() : Genre = Genre(name)

fun List<GenreResponse>.mapToModel() : List<Genre> = map { it.mapToModel() }