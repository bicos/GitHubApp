package com.ravy.data.di

import com.ravy.data.pref.AppPreference
import com.ravy.data.pref.AppPreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceModule {

    @Binds
    @Singleton
    fun bindAppPreference(
        pref: AppPreferenceImpl
    ): AppPreference
}