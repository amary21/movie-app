package com.amarydev.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("results") val results: List<ResultTvResponse>
)
