package com.example.localguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.models.ReviewModel
import com.google.android.material.snackbar.Snackbar


import timber.log.Timber
import timber.log.Timber.Forest.i

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    var review = ReviewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Review Activity started..")



        binding.btnAdd.setOnClickListener() {

            val reviewTitle = binding.reviewTitle.text.toString()
            if (reviewTitle.isNotEmpty()) {
                review.title = reviewTitle
                i("add review button pressed ${review.title}")


            } else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }
}