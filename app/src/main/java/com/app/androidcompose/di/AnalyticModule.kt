package com.app.androidcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import leegroup.module.analytics.AnalyticsManager
import leegroup.module.analytics.clients.ClientType
import leegroup.module.analytics.clients.FirebaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticModule {

    @Provides
    @Singleton
    fun provideAnalytics(): AnalyticsManager {
        return AnalyticsManager(
            listOf(FirebaseClient()),
            defaultLogEventClientType = ClientType.FIREBASE
        )
    }
}