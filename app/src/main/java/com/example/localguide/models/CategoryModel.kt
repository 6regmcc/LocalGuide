package com.example.localguide.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(var categoryName: String = "", var id: Long = 0): Parcelable {
}