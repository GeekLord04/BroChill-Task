package com.geeklord.brochilltask.di

import com.geeklord.brochilltask.Utils.Constants.BASE_URL
import com.geeklord.brochilltask.api.AuthAPI
import com.geeklord.brochilltask.api.AuthInterceptor
import com.geeklord.brochilltask.api.TweetsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofitBuilder() :  Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesAuthAPI(retrofitBuilder: Retrofit.Builder) : AuthAPI {
        return retrofitBuilder.build().create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTweetsAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient) : TweetsAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(TweetsAPI::class.java)
    }
}