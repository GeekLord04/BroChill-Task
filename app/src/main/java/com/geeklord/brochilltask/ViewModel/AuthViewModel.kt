package com.geeklord.brochilltask.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.AuthResponse
import com.geeklord.brochilltask.Repository.AuthRepositoryImpl
import com.geeklord.brochilltask.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepositoryImpl) : ViewModel() {

    private val _userLiveData = MutableLiveData<NetworkResult<AuthResponse>>()
    val userLiveData: LiveData<NetworkResult<AuthResponse>> = _userLiveData

    suspend fun registerUser(authRequest: AuthRequest) {
        _userLiveData.postValue(NetworkResult.Loading())
        val response = authRepository.registerUser(authRequest)
        _userLiveData.postValue(response)
    }

    suspend fun loginUser(authRequest: AuthRequest) {
        _userLiveData.postValue(NetworkResult.Loading())
        val response = authRepository.loginUser(authRequest)
        _userLiveData.postValue(response)
    }
}