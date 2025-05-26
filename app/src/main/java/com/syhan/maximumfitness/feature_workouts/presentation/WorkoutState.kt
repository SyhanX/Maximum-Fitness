package com.syhan.maximumfitness.feature_workouts.presentation

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutState(
    val id: Int = -1,
    val title: String = "",
    val description: String? = null,
    val type: Int = 1,
    val duration: Int = 0,
) {
    fun doesMatchSearchQuery(query: String?): Boolean {
        if (query == null) return false
        val titleWithoutSpaces = title.replace(" ", "")
        val matchingCombinations = listOf(
            title,
            title.first().toString(),
            titleWithoutSpaces,
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}