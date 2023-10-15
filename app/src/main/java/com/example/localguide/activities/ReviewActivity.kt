package com.example.localguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.material.snackbar.Snackbar


import timber.log.Timber
import timber.log.Timber.Forest.i

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    var review = ReviewModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        i("Review Activity started..")



        binding.btnAdd.setOnClickListener() {
            review.title = binding.reviewTitle.text.toString()
            review.body = binding.reviewTextBody.text.toString()
            review.rating = binding.reviewRating.rating.toDouble()
            if (review.title.isNotEmpty()) {
                app.reviews.add(review.copy())
                i("add review button pressed ${review}")
                for (i in app.reviews.indices)
                { i("Review[$i]:${this.app.reviews[i]}") }


            } else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }
}