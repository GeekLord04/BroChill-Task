package com.geeklord.brochilltask.Model

import com.google.gson.annotations.SerializedName


data class WelcomeResponse(
    @SerializedName("message")
    val message: String? = null
)