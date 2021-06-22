package com.ec.shopeasy.api.response

import com.ec.shopeasy.data.User
import com.google.gson.annotations.SerializedName

data class AuthenticateResponse (
        @SerializedName("success")
        var success: Boolean,

        @SerializedName("status")
        var status: Int,

        @SerializedName("hash")
        var hash: String,

        @SerializedName("user")
        var user: User
)