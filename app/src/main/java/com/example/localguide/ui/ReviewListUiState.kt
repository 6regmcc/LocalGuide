package com.example.localguide.ui

import com.example.localguide.models.ReviewDBModel

data class ReviewListUiState(
    val reviewList: MutableList<ReviewDBModel>? = null
)

