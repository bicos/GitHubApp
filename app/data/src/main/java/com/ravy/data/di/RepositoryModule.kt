package com.ravy.data.di

import com.ravy.data.repo.RepoRepository
import com.ravy.data.repo.RepoRepositoryImpl
import com.ravy.data.repo.UserRepository
import com.ravy.data.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepoRepository(
        repoRepository: RepoRepositoryImpl
    ): RepoRepository

    @Binds
    @Singleton
    fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}