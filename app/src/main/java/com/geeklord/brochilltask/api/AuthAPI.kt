package com.geeklord.brochilltask.api

import com.geeklord.brochilltask.Model.AuthRequest
import com.geeklord.brochilltask.Model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("/register")
    suspend fun registerUser(@Body request: AuthRequest): Response<AuthResponse>

    @POST("/login")
    suspend fun loginUser(@Body request: AuthRequest): Response<AuthResponse>
}