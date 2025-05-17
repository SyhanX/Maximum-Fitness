package com.syhan.maximumfitness.feature_workouts.data.repository

import com.syhan.maximumfitness.common.data.remote.WorkoutApi
import com.syhan.maximumfitness.feature_workouts.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(
    private val api: WorkoutApi
): WorkoutRepository {
    override suspend fun getWorkouts() = api.getWorkouts()

    override suspend fun getVideo(id: Int) = api.getWorkoutVideo(id)
}