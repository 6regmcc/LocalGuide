package com.example.localguide.models

interface JSONStore {

    fun findAllCategories(): List<CategoryModel>
    fun createCategory(category: CategoryModel)
    fun findById(id:Long) : CategoryModel?
    fun getStrArrOfCategories(): MutableList<String>



    fun findAllReviews(): List<ReviewModel>
    fun createReview(review: ReviewModel)
    fun updateReview(review: ReviewModel)

    fun deleteReview(review: ReviewModel)

    fun findReviewById(id:Long) : ReviewModel?
}