package com.syhan.maximumfitness.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.syhan.maximumfitness.common.data.remote.WorkoutApi
import com.syhan.maximumfitness.common.di.RetrofitConstants.BASE_URL
import com.syhan.maximumfitness.feature_workouts.data.repository.WorkoutRepositoryImpl
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository
import com.syhan.maximumfitness.feature_workouts.presentation.workout_details.WorkoutDetailsViewModel
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.WorkoutListViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WorkoutApi::class.java)
    }

    single<WorkoutRepository> {
        WorkoutRepositoryImpl(get())
    }

    viewModelOf(::WorkoutListViewModel)
    viewModelOf(::WorkoutDetailsViewModel)
}

object RetrofitConstants {
    const val BASE_URL = "https://ref.test.kolsa.ru/"
}