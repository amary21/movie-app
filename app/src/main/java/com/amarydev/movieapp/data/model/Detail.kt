package com.amarydev.movieapp.data.model

data class Detail(
    var backdropPath: String,
    var genres: List<Genre>,
    var homepage: String,
    var overview: String,
    var posterPath: String,
    var productionCompanies: List<Production>,
    var releaseDate: String,
    var voteAverage: Double
)
