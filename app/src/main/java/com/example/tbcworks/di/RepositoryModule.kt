package com.example.tbcworks.di

import com.example.tbcworks.data.repository.EventRepositoryImpl
import com.example.tbcworks.data.repository.NotificationRepositoryImpl
import com.example.tbcworks.data.repository.SignInRepositoryImpl
import com.example.tbcworks.data.repository.SignUpRepositoryImpl
import com.example.tbcworks.data.repository.UserRepositoryImpl
import com.example.tbcworks.domain.repository.EventRepository
import com.example.tbcworks.domain.repository.NotificationRepository
import com.example.tbcworks.domain.repository.SignInRepository
import com.example.tbcworks.domain.repository.SignUpRepository
import com.example.tbcworks.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEventRepository(
        impl: EventRepositoryImpl
    ): EventRepository

    @Binds
    abstract fun bindSignUpRepository(
        impl: SignUpRepositoryImpl
    ): SignUpRepository
    @Binds
    abstract fun bindSignInRepository(
        impl: SignInRepositoryImpl
    ): SignInRepository
    @Binds
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

}