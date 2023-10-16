package com.example.localguide.models
import timber.log.Timber.Forest.i

class ReviewMemStore : ReviewStore {

    val reviews = ArrayList<ReviewModel>()

    override fun findAll(): List<ReviewModel> {
        return reviews
    }

    override fun create(review: ReviewModel) {
        reviews.add(review)
        logAll()
    }

    fun logAll() {
        reviews.forEach{ i("${it}") }
    }
}