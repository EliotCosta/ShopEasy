package com.ec.shopeasy.data

import com.ec.shopeasy.data.Product
import com.google.gson.annotations.SerializedName

data class ProductCategories(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("urlImg")
    var urlImg: String,

    @SerializedName("products")
    var products: List<Product>
)