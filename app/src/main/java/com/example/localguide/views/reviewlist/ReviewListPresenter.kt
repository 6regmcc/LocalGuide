package com.example.localguide.views.reviewlist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.localguide.views.map.ReviewMapView
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.example.localguide.views.review.ReviewView

class ReviewListPresenter (val view: ReviewListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getReviews() = app.combinedStore.findAllReviews()

    fun doAddReview() {
        val launcherIntent = Intent(view, ReviewView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditReview(review: ReviewModel, pos: Int) {
        val launcherIntent = Intent(view, ReviewView::class.java)
        launcherIntent.putExtra("review_edit", review)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowReviewsMap() {
        val launcherIntent = Intent(view, ReviewMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }





}