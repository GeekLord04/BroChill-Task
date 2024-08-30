package com.geeklord.brochilltask.Repository

import android.util.Log
import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.AuthResponse
import com.geeklord.brochilltask.Utils.Constants.TAG
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.api.AuthAPI
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authAPI: AuthAPI) : AuthRepository {
    override suspend fun registerUser(authRequest: AuthRequest): NetworkResult<AuthResponse> {
        val response = authAPI.registerUser(authRequest)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        }
        if (response.code() == 409){
            return NetworkResult.Error("User Already Exist. Please Login")
        }
        return NetworkResult.Error("${response.errorBody()}")
    }

    override suspend fun loginUser(authRequest: AuthRequest): NetworkResult<AuthResponse> {
        val response = authAPI.loginUser(authRequest)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        }
        if (response.code() == 400){
            return NetworkResult.Error("Invalid Credentials")
        }
        Log.d(TAG, "loginUser: ${response.errorBody()}")
        return NetworkResult.Error(response.errorBody().toString())
    }

}