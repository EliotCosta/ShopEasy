package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.Shop
import com.google.gson.annotations.SerializedName

data class ShopsResponse (
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("status")
    var status: Int,

    @SerializedName("shops")
    var shops: List<Shop>
)