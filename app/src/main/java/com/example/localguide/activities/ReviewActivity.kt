package com.example.localguide.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.localguide.R
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.material.snackbar.Snackbar
import com.example.localguide.helpers.showImagePicker
import com.example.localguide.models.CategoryModel
import com.example.localguide.models.Location
import com.squareup.picasso.Picasso


import timber.log.Timber.Forest.i

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    var review = ReviewModel()
    lateinit var app: MainApp
    var edit = false
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        edit = false


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
            if (review.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_review_image)
            }
        }

        val spinner: Spinner = binding.categorySpinner
// Create an ArrayAdapter using the string array and a default spinner layout.
        val categoryStrArray: MutableList<String> = mutableListOf()


        val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, app.categories.getStringArray())
        spinner.adapter = adapter

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
            showImagePicker(imageIntentLauncher,this)
        }



        binding.reviewLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (review.zoom != 0f) {
                location.lat =  review.lat
                location.lng = review.lng
                location.zoom = review.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.createNewCategoryTV.setOnClickListener{
                startActivity(Intent(this@ReviewActivity, CreateCategoryActivity::class.java))
        }




        registerImagePickerCallback()
        registerMapCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_local_guide, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.reviews.delete(review)
                finish()
            }
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
                            binding.chooseImage.setText(R.string.change_review_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            review.lat = location.lat
                            review.lng = location.lng
                            review.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }




}