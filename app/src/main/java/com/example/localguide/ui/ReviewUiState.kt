package com.example.localguide.ui

import com.example.localguide.models.ReviewModel

data class ReviewUiState(
    val exampleReview: ReviewModel = ReviewModel(title="ReviewViewModelExampel", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg"),
    //val currentReview: ReviewModel = exampleReview,
    val reviewTitle: String = "",
    val reviewBody: String = ""
)