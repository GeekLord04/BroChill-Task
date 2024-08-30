package com.geeklord.brochilltask.Repository

import android.util.Log
import com.geeklord.brochilltask.Model.GetTweetResponse
import com.geeklord.brochilltask.Model.PostTweetRequest
import com.geeklord.brochilltask.Model.PostTweetResponse
import com.geeklord.brochilltask.Model.WelcomeResponse
import com.geeklord.brochilltask.Utils.Constants.TAG
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.api.TweetsAPI
import org.json.JSONObject
import javax.inject.Inject

class TweetRepositoryImpl @Inject constructor(private val tweetsAPI: TweetsAPI) : TweetRepository {
    override suspend fun postTweet(tweetRequest: PostTweetRequest): NetworkResult<PostTweetResponse> {
        val response = tweetsAPI.postTweet(tweetRequest)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        }
        if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            Log.d(TAG, "postTweet: ${errorObj.getString("message")} + ${response.code()}")
            return NetworkResult.Error(errorObj.getString("message"))
        }
        return NetworkResult.Error(response.errorBody().toString())
    }

    override suspend fun getTweets(): NetworkResult<List<GetTweetResponse>> {
        val response = tweetsAPI.getTweets()
        if (response.isSuccessful && response.body() != null){
            return NetworkResult.Success(response.body()!!)
        }
        return NetworkResult.Error(response.errorBody().toString())
    }

    override suspend fun welcome(): NetworkResult<WelcomeResponse> {
        val response = tweetsAPI.welcome()
        if (response.isSuccessful && response.body() != null){
            return NetworkResult.Success(response.body()!!)
        }
        if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            Log.d(TAG, "welcome: ${errorObj.getString("message")} + ${response.code()}")
            return NetworkResult.Error(errorObj.getString("message"))
        }
        return NetworkResult.Error(response.errorBody().toString())
    }


}