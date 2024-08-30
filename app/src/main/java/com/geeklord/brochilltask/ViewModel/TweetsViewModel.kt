package com.geeklord.brochilltask.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeklord.brochilltask.Model.GetTweetResponse
import com.geeklord.brochilltask.Model.PostTweetRequest
import com.geeklord.brochilltask.Model.PostTweetResponse
import com.geeklord.brochilltask.Repository.TweetRepositoryImpl
import com.geeklord.brochilltask.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(private val tweetsRepository: TweetRepositoryImpl) : ViewModel() {

    private val _postTweetLiveData = MutableLiveData<NetworkResult<PostTweetResponse>>()
    val postTweetLiveData: LiveData<NetworkResult<PostTweetResponse>> = _postTweetLiveData

    private val _getTweetsLiveData = MutableLiveData<NetworkResult<List<GetTweetResponse>>>()
    val getTweetsLiveData: LiveData<NetworkResult<List<GetTweetResponse>>> = _getTweetsLiveData

    suspend fun postTweet(tweetRequest: PostTweetRequest) {
        _postTweetLiveData.postValue(NetworkResult.Loading())
        val response = tweetsRepository.postTweet(tweetRequest)
        _postTweetLiveData.postValue(response)
    }

    suspend fun getTweets() {
        _getTweetsLiveData.postValue(NetworkResult.Loading())
        val response = tweetsRepository.getTweets()
        _getTweetsLiveData.postValue(response)
    }

}