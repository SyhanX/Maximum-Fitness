package com.syhan.maximumfitness.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.syhan.maximumfitness.common.data.remote.WorkoutApi
import com.syhan.maximumfitness.common.data.repository.WorkoutRepositoryImpl
import com.syhan.maximumfitness.feature_exercises.domain.repository.WorkoutRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.jvm.java

val appModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WorkoutApi::class.java)
    }

    single<WorkoutRepository> {
        WorkoutRepositoryImpl(get())
    }
}