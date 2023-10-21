package com.example.localguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.localguide.R
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
    var edit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)



        app = application as MainApp
        i("Review Activity started..")

        if (intent.hasExtra("review_edit")) {
            edit = true
            review = intent.extras?.getParcelable("review_edit")!!
            binding.reviewTitle.setText(review.title)
            binding.reviewTextBody.setText(review.body)
            binding.btnAdd.setText(R.string.save_review)
        }


        binding.btnAdd.setOnClickListener() {
            review.title = binding.reviewTitle.text.toString()
            review.body = binding.reviewTextBody.text.toString()
            review.rating = binding.reviewRating.rating.toDouble()

            if (review.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_review_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.reviews.update(review.copy())
                } else {
                    app.reviews.create(review.copy())
                }
                setResult(RESULT_OK)
                finish()
            }



        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}