package com.example.localguide.main

import android.app.Application
import com.example.localguide.models.CategoryStore
import com.example.localguide.models.ReviewJSONStore
import com.example.localguide.models.ReviewMemStore
import com.example.localguide.models.ReviewStore
import timber.log.Timber
import timber.log.Timber.Forest.i
class MainApp : Application() {
    lateinit var reviews: ReviewStore
    lateinit var categories: ArrayList<String>
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        reviews = ReviewJSONStore(applicationContext)
        val array = arrayListOf("shop", "pub", "post office", "bus stop")
        categories = array
        i("Local Guide started")


    }
}