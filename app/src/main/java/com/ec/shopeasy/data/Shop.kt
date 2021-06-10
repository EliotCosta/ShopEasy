package com.ec.shopeasy.data

import com.google.gson.annotations.SerializedName

data class Shop (
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("urlMap")
    var urlMap: String,

    @SerializedName("mapScale")
    var mapScale: Float
)