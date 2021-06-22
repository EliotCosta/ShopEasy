package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("success")
        var success: Boolean,

        @SerializedName("status")
        var status: Int,

        @SerializedName("user")
        var user: User
)