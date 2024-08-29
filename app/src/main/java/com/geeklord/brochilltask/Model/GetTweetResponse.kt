package com.geeklord.brochilltask.Model

import com.google.gson.annotations.SerializedName


data class GetTweetResponse(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("tweet")
    val tweet: String? = null,
    @SerializedName("__v")
    val v: Int? = null
)