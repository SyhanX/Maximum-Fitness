package com.syhan.maximumfitness.feature_workouts.data


import com.syhan.maximumfitness.R

fun getMinutesDeclension(duration: Int): Int {
    /* thx russian language for being so complicated */
    return if (duration in 11..14) {
        R.string.minutes_plural_genitive_case
    } else {
        when (duration % 10) {
            1 -> R.string.minute_singular_nominative_case
            2, 3, 4 -> R.string.minute_singular_genitive_case
            0, 5, 6, 7, 8, 9 -> R.string.minutes_plural_genitive_case
            else -> R.string.unknown_duration
        }
    }
}