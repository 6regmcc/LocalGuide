package com.example.localguide.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.localguide.databinding.ActivityReviewMapsBinding
import com.example.localguide.databinding.ContentReviewMapsBinding
import com.example.localguide.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class ReviewMapsActivity : AppCompatActivity(),  GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityReviewMapsBinding
    private lateinit var contentBinding: ContentReviewMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityReviewMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentReviewMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

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

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        app.reviews.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        //val placemark = marker.tag as PlacemarkModel
        val tag = marker.tag as Long
        val review = app.reviews.findById(tag)
        contentBinding.currentTitle.text = review!!.title
        contentBinding.currentDescription.text = review!!.body //fix currentDescription.text later
        Picasso.get().load(review.image).into(contentBinding.currentImage)
        return false
    }
}