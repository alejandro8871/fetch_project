package com.fetch.fetch.di

import android.content.Context
import com.fetch.fetch.data.local.HiringDAO
import com.fetch.fetch.database.FetchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FetchDatabase {
        return FetchDatabase.getDatabase(context)
    }

    @Provides
    fun provideFetchDao(database: FetchDatabase): HiringDAO {
        return database.hiringDAO()
    }
}