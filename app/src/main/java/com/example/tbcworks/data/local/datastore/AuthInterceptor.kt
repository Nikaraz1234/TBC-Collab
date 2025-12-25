package com.example.tbcworks.data.local.datastore

import com.example.tbcworks.domain.repository.DataStoreManager
import com.example.tbcworks.domain.usecase.datastore.PreferencesKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AuthTokenHolder.token.orEmpty()

        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("x-api-key", "reqres-free-v1")
            .apply {
                if (token.isNotEmpty()) addHeader("Authorization", "Bearer $token")
            }
            .build()

        return chain.proceed(request)
    }
}