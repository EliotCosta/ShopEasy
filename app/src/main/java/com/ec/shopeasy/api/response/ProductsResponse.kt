package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.ProductCategories
import com.google.gson.annotations.SerializedName

data class ProductsResponse (
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("status")
    var status: Int,

    @SerializedName("shop")
    var productCategories: List<ProductCategories>
)