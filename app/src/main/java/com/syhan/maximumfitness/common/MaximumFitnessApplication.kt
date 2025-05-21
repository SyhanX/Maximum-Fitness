package com.syhan.maximumfitness.common

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.syhan.maximumfitness.common.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MaximumFitnessApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MaximumFitnessApplication)
            modules(appModule)
        }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}