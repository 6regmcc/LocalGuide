package com.example.localguide.models

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ReviewDBModel(
    val reviewId: String? = null,
    val title: String? = null,
    val body: String? = null,
    val uri: Uri? = null,
    val imageURl: String? = null,
    val userId: String? = null,
    val latitude: String? = "",
    val longitude: String? = "",
    )