package com.syhan.maximumfitness.common

import android.app.Application
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
    }
}