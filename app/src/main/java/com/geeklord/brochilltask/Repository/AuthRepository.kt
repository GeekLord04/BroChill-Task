package com.geeklord.brochilltask.Repository

import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.AuthResponse
import com.geeklord.brochilltask.Utils.NetworkResult

interface AuthRepository {

    suspend fun registerUser(authRequest: AuthRequest): NetworkResult<AuthResponse>

    suspend fun loginUser(authRequest: AuthRequest): NetworkResult<AuthResponse>

}