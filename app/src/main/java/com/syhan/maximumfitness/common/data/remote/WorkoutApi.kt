package com.syhan.maximumfitness.common.data.remote

import com.syhan.maximumfitness.feature_workouts.domain.model.Workout
import com.syhan.maximumfitness.feature_workouts.domain.model.WorkoutVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WorkoutApi {

    @GET("/get_workouts")
    suspend fun getWorkouts() : Response<List<Workout>>

    @GET("/get_video")
    suspend fun getWorkoutVideo(@Query("id") id: Int) : Response<WorkoutVideo>
}