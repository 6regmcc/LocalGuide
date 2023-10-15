package com.example.localguide.main

import android.app.Application
import com.example.localguide.models.ReviewModel
import timber.log.Timber
import timber.log.Timber.Forest.i
class MainApp : Application() {
    val reviews = ArrayList<ReviewModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Local Guide started")

        reviews.add(ReviewModel("One", "About one...",0.0))
        reviews.add(ReviewModel("Two", "About two...", 0.0))
        reviews.add(ReviewModel("Three", "About three...", 0.0))
    }
}