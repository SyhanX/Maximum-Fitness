package com.syhan.maximumfitness.feature_exercises.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutVideo(
    val id: Int,
    val duration: String,
    val link: String
)
