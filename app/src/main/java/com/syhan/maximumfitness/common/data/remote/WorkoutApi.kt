package com.syhan.maximumfitness.common.data.remote

import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutList
import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WorkoutApi {

    @GET("/get_workouts")
    suspend fun getWorkouts() : Response<WorkoutList>

    @GET("/get_video")
    suspend fun getWorkoutVideo(@Query("id") id: Int) : Response<WorkoutVideo>
}