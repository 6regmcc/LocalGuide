package com.example.localguide.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.localguide.R
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.material.snackbar.Snackbar
import com.example.localguide.helpers.showImagePicker
import com.squareup.picasso.Picasso


import timber.log.Timber
import timber.log.Timber.Forest.i

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    var review = ReviewModel()
    lateinit var app: MainApp
    var edit = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>


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
            Picasso.get()
                .load(review.image)
                .into(binding.reviewImage)
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()

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



    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            review.image = result.data!!.data!!
                            Picasso.get()
                                .load(review.image)
                                .into(binding.reviewImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }




}