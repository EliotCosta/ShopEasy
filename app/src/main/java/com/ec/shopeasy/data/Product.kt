package com.ec.shopeasy.data

import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
)