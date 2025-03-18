package com.fetch.fetch.di

import com.fetch.fetch.utils.AndroidLogger
import com.fetch.fetch.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    fun provideLogger(): Logger {
        return AndroidLogger()
    }
}