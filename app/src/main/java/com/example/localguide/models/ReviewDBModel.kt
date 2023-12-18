package com.example.localguide.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ReviewDBModel(
    val reviewId: String? = null,
    val title: String? = null,
    val body: String? = null,
    val userId: String? = null)