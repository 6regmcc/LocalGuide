package com.example.localguide.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReviewViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    private lateinit var reviewTitle: String
    private lateinit var reviewBody: String
    lateinit var app: MainApp
    //val exampleReview: ReviewModel = ReviewModel(title="", body="", category = "", rating = 0.0, imageURL = "")


    var editedReviewTitle by mutableStateOf("starting value")
        private set

    private fun getCurrentReview(id: Long): ReviewModel? {
        return app.combinedStore.findReviewById(id)
    }

    fun updateReviewTitle(reviewTitleEdited: String) {
        editedReviewTitle = reviewTitleEdited
    }

}