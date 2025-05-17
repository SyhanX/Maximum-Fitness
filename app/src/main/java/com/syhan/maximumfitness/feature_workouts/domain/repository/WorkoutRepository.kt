package com.syhan.maximumfitness.feature_workouts.domain.repository

import com.syhan.maximumfitness.feature_workouts.domain.model.Workout
import com.syhan.maximumfitness.feature_workouts.domain.model.WorkoutVideo
import retrofit2.Response

interface WorkoutRepository {
    suspend fun getWorkouts() : Response<List<Workout>>
    suspend fun getVideo(id: Int) : Response<WorkoutVideo>
}