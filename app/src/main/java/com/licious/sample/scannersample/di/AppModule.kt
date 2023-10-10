package com.licious.sample.scannersample.di

import com.licious.sample.design.ui.permission.PermissionUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getPermissionUtil(): PermissionUtil {
        return PermissionUtil()
    }
}