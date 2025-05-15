package com.syhan.maximumfitness.common.data.repository

import com.syhan.maximumfitness.common.data.remote.WorkoutApi
import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutList
import com.syhan.maximumfitness.feature_exercises.domain.model.WorkoutVideo
import com.syhan.maximumfitness.feature_exercises.domain.repository.WorkoutRepository
import retrofit2.Response

class WorkoutRepositoryImpl(
    private val api: WorkoutApi
): WorkoutRepository {
    override suspend fun getWorkouts() = api.getWorkouts()

    override suspend fun getVideo(id: Int) = api.getWorkoutVideo(id)
}