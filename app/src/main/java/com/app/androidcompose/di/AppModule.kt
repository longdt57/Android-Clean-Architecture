package com.app.androidcompose.di

import android.content.Context
import com.app.androidcompose.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.core.util.AppConfigurationProvider
import leegroup.module.core.util.DispatchersProvider
import leegroup.module.core.util.DispatchersProviderImpl

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl

    @Provides
    fun provideAppConfigurationProvider(): AppConfigurationProvider = object : AppConfigurationProvider {
        override val debug: Boolean
            get() = BuildConfig.DEBUG
        override val buildType: String
            get() = BuildConfig.BUILD_TYPE
        override val flavor: String
            get() = BuildConfig.FLAVOR
        override val versionCode: Int
            get() = BuildConfig.VERSION_CODE
        override val versionName: String
            get() = BuildConfig.VERSION_NAME

    }
}
