package com.amarydev.core.data.source.remote.response

import com.amarydev.domain.model.Production
import com.google.gson.annotations.SerializedName

data class ProductionResponse (
    @SerializedName("name") val name: String
)

fun ProductionResponse.mapToModel() : Production = Production(name)

fun List<ProductionResponse>.mapToModel(): List<Production> = map { it.mapToModel() }