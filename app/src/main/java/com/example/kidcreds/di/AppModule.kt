package com.example.kidcreds.di

import com.example.kidcreds.data.repository.UserRepo
import com.example.kidcreds.data.repositoryimpl.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepoImpl: UserRepoImpl): UserRepo
}