package com.example.localguide.models

interface ReviewStore {
    fun findAll(): List<ReviewModel>
    fun create(review: ReviewModel)
    fun update(review: ReviewModel)

    fun delete(review: ReviewModel)

    fun findById(id:Long) : ReviewModel?






}