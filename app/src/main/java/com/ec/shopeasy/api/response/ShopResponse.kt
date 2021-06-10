package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.Shop
import com.google.gson.annotations.SerializedName

data class ShopResponse (
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("status")
    var status: Int,

    @SerializedName("shop")
    var shop: Shop
)