package com.example.localguide.main

import android.app.Application


//import com.example.localguide.models.ReviewJSONStore
import timber.log.Timber
import timber.log.Timber.Forest.i
class MainApp : Application() {
    //lateinit var reviews: ReviewStore
    //var categories = CategoryMemStore()
    //lateinit var combinedStore: JSONStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //reviews = ReviewJSONStore(applicationContext)
        //combinedStore = CombinedJSONStore(applicationContext)
        //categories = CategoryMemStore()
        //categories.add(CategoryModel("Pub"))
        //categories.add(CategoryModel("Post Office"))
        //categories.add(CategoryModel("Bus Stop"))
        i("Local Guide started")


    }
}