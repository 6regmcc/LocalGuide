package com.example.localguide.main

import android.app.Application
import com.example.localguide.models.ReviewMemStore
import com.example.localguide.models.ReviewModel
import timber.log.Timber
import timber.log.Timber.Forest.i
class MainApp : Application() {
    val reviews = ReviewMemStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Local Guide started")


    }
}