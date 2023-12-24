
package com.example.localguide.ui

import android.net.Uri
import com.google.android.gms.maps.model.LatLng


data class ReviewUiState(
    //val exampleReview: ReviewModel = ReviewModel(title="ReviewViewModelExampel", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg"),
    //val currentReview: ReviewModel = exampleReview,
    val reviewTitle: String = "",
    val reviewBody: String = "",
    val dbRightSuccess: Boolean = false,
    val dbError: Boolean = false,
    val imageUri: Uri? = null,
    val showProgressSpinner: Boolean = false,
    val reviewFound: Boolean = false,
    val enableReviewEdit: Boolean = false,
    val imageURL: String? = "",
    val isLoggedInUserIsOwnerOfReview: Boolean = false,
    val latitude: String? = "",
    val longitude: String? = "",
    val isLocationValue: Boolean = false
)