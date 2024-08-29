package com.geeklord.brochilltask.api

import com.geeklord.brochilltask.Utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager : TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =  chain.request().newBuilder()

        val token = tokenManager.getToken()
        request.addHeader("x-api-key","$token")

        return chain.proceed(request.build())
    }
}