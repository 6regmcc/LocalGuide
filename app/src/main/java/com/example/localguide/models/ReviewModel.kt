package com.example.localguide.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewModel(var id: Long = 0, var title: String = "", var body: String = "", var rating: Double = 0.0,  var image: Uri = Uri.EMPTY): Parcelable {

}


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable