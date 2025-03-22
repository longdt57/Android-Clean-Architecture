package com.app.androidcompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.designsystem.support.util.DispatchersProvider
import leegroup.module.designsystem.support.util.DispatchersProviderImpl

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl
}
