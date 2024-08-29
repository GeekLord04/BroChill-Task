package com.geeklord.brochilltask.Model


import com.google.gson.annotations.SerializedName

data class PostTweetResponse(
    @SerializedName("tweet")
    val tweet: String? = null,

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("__v")
    val version: Int? = null
)
