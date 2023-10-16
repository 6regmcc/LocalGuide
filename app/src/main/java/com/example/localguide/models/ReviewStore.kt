package com.example.localguide.models

interface ReviewStore {
    fun findAll(): List<ReviewModel>
    fun create(review: ReviewModel)
}