package com.example.russiatravel.di

import com.example.russiatravel.network.RetrofitService
import com.example.russiatravel.repository.LocationRepository
import com.example.russiatravel.repository.SightRepository
import com.example.russiatravel.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(retrofitService: RetrofitService) = UserRepository (retrofitService)

    @Singleton
    @Provides
    fun provideLocationRepository (retrofitService: RetrofitService) = LocationRepository(retrofitService)

    @Singleton
    @Provides
    fun provideSightRepository (retrofitService: RetrofitService) = SightRepository(retrofitService)
}