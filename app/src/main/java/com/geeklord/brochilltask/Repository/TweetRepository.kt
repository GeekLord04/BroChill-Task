package com.geeklord.brochilltask.Repository

import com.geeklord.brochilltask.Model.GetTweetResponse
import com.geeklord.brochilltask.Model.PostTweetRequest
import com.geeklord.brochilltask.Model.PostTweetResponse
import com.geeklord.brochilltask.Utils.NetworkResult

interface TweetRepository {

    suspend fun postTweet(tweetRequest: PostTweetRequest): NetworkResult<PostTweetResponse>

    suspend fun getTweets(): NetworkResult<List<GetTweetResponse>>

}