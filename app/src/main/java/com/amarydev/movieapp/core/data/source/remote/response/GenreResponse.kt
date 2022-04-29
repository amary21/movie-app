package com.amarydev.movieapp.core.data.source.remote.response

import com.amarydev.movieapp.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("name") val name: String
)

fun GenreResponse.mapToModel() : Genre = Genre(name)

fun List<GenreResponse>.mapToModel() : List<Genre> = map { it.mapToModel() }