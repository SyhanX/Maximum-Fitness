package com.syhan.maximumfitness.feature_workouts.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutVideo(
    val id: Int,
    val duration: Int,
    val link: String
)
