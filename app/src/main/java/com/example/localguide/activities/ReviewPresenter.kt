package com.example.localguide.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.helpers.showImagePicker
import com.example.localguide.main.MainApp
import com.example.localguide.models.CategoryModel
import com.example.localguide.models.Location
import com.example.localguide.models.ReviewModel
import timber.log.Timber

class ReviewPresenter (private val view: ReviewView) {

    var review = ReviewModel()
    var category = CategoryModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityReviewBinding = ActivityReviewBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("review_edit")) {
            edit = true
            review = view.intent.extras?.getParcelable("review_edit")!!
            view.showReview(review)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            review.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(review.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(review.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            review.lat = location.lat
                            review.lng = location.lng
                            review.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }


    fun doAddOrSave(title: String, body: String) {
        review.title = title
        review.body = body
        if (edit) {
            app.combinedStore.updateReview(review)
        } else {
            app.combinedStore.createReview(review)
        }
        view.setResult(Activity.RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.combinedStore.deleteReview(review)
        view.finish()
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (review.zoom != 0f) {
            location.lat =  review.lat
            location.lng = review.lng
            location.zoom = review.zoom
        }
        val launcherIntent = Intent(view, ReviewMapsActivity::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cachePlacemark (title: String, body: String) {
        review.title = title;
        review.body = body
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

}