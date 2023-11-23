package com.example.localguide.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.localguide.databinding.ActivityReviewMapsBinding
import com.example.localguide.databinding.ContentReviewMapsBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso

class ReviewMapView : AppCompatActivity(),  GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityReviewMapsBinding
    private lateinit var contentBinding: ContentReviewMapsBinding

    lateinit var app: MainApp

    lateinit var presenter: ReviewMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityReviewMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = ReviewMapPresenter(this)

        contentBinding = ContentReviewMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
          presenter.doPopulateMap(it)
        }

    }

    fun showReview(review: ReviewModel) {
        contentBinding.currentTitle.text = review.title
        contentBinding.currentDescription.text = review.body
        Picasso.get()
            .load(review.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }



}