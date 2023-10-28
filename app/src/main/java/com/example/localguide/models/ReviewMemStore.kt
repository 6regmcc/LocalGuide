package com.example.localguide.models
import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ReviewMemStore : ReviewStore {

    val reviews = ArrayList<ReviewModel>()

    override fun findAll(): List<ReviewModel> {
        return reviews
    }

    override fun create(review: ReviewModel) {
        review.id = getId()
        reviews.add(review)
        logAll()
    }



    override fun update(review: ReviewModel) {
        var foundReview: ReviewModel? = reviews.find { p -> p.id == review.id }
        if (foundReview != null) {
            foundReview.title = review.title
            foundReview.body = review.body
            foundReview.image = review.image
            foundReview.lat = review.lat
            foundReview.lng = review.lng
            foundReview.zoom = review.zoom
            logAll()
        }
    }

    fun logAll() {
        reviews.forEach{ i("${it}") }
    }

    override fun delete(placemark: ReviewModel) {
        reviews.remove(placemark)
    }

    override fun findById(id:Long) : ReviewModel? {
        val foundReview: ReviewModel? = reviews.find { it.id == id }
        return foundReview
    }


}