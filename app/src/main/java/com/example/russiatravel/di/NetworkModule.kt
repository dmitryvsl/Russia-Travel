package com.example.russiatravel.di

import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.repository.UserRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val baseInterceptor: Interceptor = invoke { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()

        return@invoke chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(baseInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService{
        return Retrofit.Builder()
            .baseUrl("http://guiderapi.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(retrofitService: RetrofitService) = UserRepository (retrofitService)

}