package com.amarydev.movieapp.data.source.remote.response

import com.amarydev.movieapp.data.model.Production
import com.google.gson.annotations.SerializedName

data class ProductionResponse (
    @SerializedName("name") val name: String
)

fun ProductionResponse.mapToModel() : Production = Production(name)

fun List<ProductionResponse>.mapToModel(): List<Production> = map { it.mapToModel() }