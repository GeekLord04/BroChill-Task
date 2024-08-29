package com.geeklord.brochilltask.Model

import com.google.gson.annotations.SerializedName

data class PostTweetRequest(
    @SerializedName("tweet")
    val tweet: String? = null
)
