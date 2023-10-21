package com.example.localguide.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(var id: Long = 0, var title: String = "", var body: String = "", var rating: Double = 0.0): Parcelable {

}
