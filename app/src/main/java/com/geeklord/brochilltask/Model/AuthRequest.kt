package com.geeklord.brochilltask.Model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null
)
