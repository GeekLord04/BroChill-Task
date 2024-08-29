package com.geeklord.brochilltask.api

import com.geeklord.brochilltask.Model.GetTweetResponse
import com.geeklord.brochilltask.Model.PostTweetRequest
import com.geeklord.brochilltask.Model.PostTweetResponse
import com.geeklord.brochilltask.Model.WelcomeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TweetsAPI {
    @POST("/tweet")
    suspend fun postTweet(@Body request: PostTweetRequest): Response<PostTweetResponse>

    @GET("/tweet")
    suspend fun getTweets(): Response<List<GetTweetResponse>>

    @GET("/welcome")
    suspend fun welcome(): Response<WelcomeResponse>

}