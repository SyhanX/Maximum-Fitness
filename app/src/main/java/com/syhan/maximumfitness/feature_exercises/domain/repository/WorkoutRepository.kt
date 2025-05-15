package com.syhan.maximumfitness.feature_exercises.domain.repository

import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutList
import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutVideo
import retrofit2.Response

interface WorkoutRepository {
    suspend fun getWorkouts() : Response<WorkoutList>
    suspend fun getVideo(id: Int) : Response<WorkoutVideo>
}