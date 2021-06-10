package com.ec.shopeasy.data

import com.google.gson.annotations.SerializedName

data class ShopSection (
    @SerializedName("id")
    var id: Int,

    @SerializedName("idShop")
    var idShop: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("x")
    var x: Float,

    @SerializedName("y")
    var y: Float,

    @SerializedName("productList")
    var productList: List<Int>

)
