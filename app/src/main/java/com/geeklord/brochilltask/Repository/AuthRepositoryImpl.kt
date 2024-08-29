package com.geeklord.brochilltask.Repository

import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.AuthResponse
import com.geeklord.brochilltask.Utils.NetworkResult
import com.geeklord.brochilltask.api.AuthAPI
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authAPI: AuthAPI) : AuthRepository {
    override suspend fun registerUser(authRequest: AuthRequest): NetworkResult<AuthResponse> {
        val response = authAPI.registerUser(authRequest)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        }
        return NetworkResult.Error("${response.errorBody()}")
    }

    override suspend fun loginUser(authRequest: AuthRequest): NetworkResult<AuthResponse> {
        val response = authAPI.loginUser(authRequest)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        }
        return NetworkResult.Error("${response.errorBody()}")
    }

}