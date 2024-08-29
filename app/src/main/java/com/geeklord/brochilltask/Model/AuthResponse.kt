package com.geeklord.brochilltask.Model

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("token")
    val token: String? = null
)