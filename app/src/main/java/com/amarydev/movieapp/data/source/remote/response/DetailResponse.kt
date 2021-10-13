package com.amarydev.movieapp.data.source.remote.response

import com.amarydev.movieapp.data.model.Detail
import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("backdrop_path") var backdropPath: String,
    @SerializedName("genres") var genres: List<GenreResponse>,
    @SerializedName("homepage") var homepage: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("production_companies") var productionResponseCompanies: List<ProductionResponse>,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("vote_average") var voteAverage: Double
)

fun DetailResponse.mapToModel() : Detail = Detail(
    backdropPath,
    genres.mapToModel(),
    homepage,
    overview,
    posterPath,
    productionResponseCompanies.mapToModel(),
    releaseDate,
    voteAverage
)
