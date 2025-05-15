package com.syhan.maximumfitness.feature_workouts.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    val id: Int,
    val title: String,
    val description: String? = null,
    val type: Int,
    val duration: String
)

@Serializable
data class WorkoutList(
    val workouts: List<Workout>
)