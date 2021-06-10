package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.ShopSection
import com.google.gson.annotations.SerializedName

data class ShopSectionsResponse (
    @SerializedName("success")
    var success: Boolean,

    @SerializedName("status")
    var status: Int,

    @SerializedName("sections")
    var sections: List<ShopSection>
)