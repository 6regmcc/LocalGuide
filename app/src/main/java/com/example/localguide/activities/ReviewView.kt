package com.example.localguide.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.squareup.picasso.Picasso


import timber.log.Timber.Forest.i

class ReviewView : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding
    private lateinit var presenter: ReviewPresenter

    var review = ReviewModel()
    var category = CategoryModel()

    private lateinit var auth: FirebaseAuth
    lateinit var categoryArrayOfStrings: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>
    //var location = Location(52.245696, -7.139102, 15f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = ReviewPresenter(this)

       //come back to thsi









        //categoryArrayOfStrings = app.combinedStore.getStrArrOfCategories()

        //adapter = ArrayAdapter<String>(this, R.layout.dropdown_item, categoryArrayOfStrings)
        //binding.autoCompleteTextView.setAdapter(adapter)



        binding.chooseImage.setOnClickListener {
            presenter.cachePlacemark(binding.reviewTitle.text.toString(), binding.reviewTextBody.text.toString())
            presenter.doSelectImage()
        }



        binding.reviewLocation.setOnClickListener {
            presenter.cachePlacemark(binding.reviewTitle.text.toString(), binding.reviewTextBody.text.toString())
            presenter.doSetLocation()
        }

        binding.createNewCategoryTV.setOnClickListener{
                startActivity(Intent(this@ReviewView, CreateCategoryActivity::class.java))
        }






    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_local_guide, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.reviewTitle.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_review_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    // presenter.cachePlacemark(binding.placemarkTitle.text.toString(), binding.description.text.toString())
                    presenter.doAddOrSave(binding.reviewTitle.text.toString(), binding.reviewTextBody.text.toString())
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun showReview(review: ReviewModel) {
        binding.reviewTitle.setText(review.title)
        binding.reviewTextBody.setText(review.body)
        Picasso.get()
            .load(review.image)
            .into(binding.reviewImage)
        if (review.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_review_image)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.reviewImage)
        binding.chooseImage.setText(R.string.change_review_image)
    }

    /*
    override fun onResume() {
        super.onResume()
        categoryArrayOfStrings = app.combinedStore.getStrArrOfCategories()
        adapter = ArrayAdapter<String>(this, R.layout.dropdown_item, categoryArrayOfStrings)
        binding.autoCompleteTextView.setAdapter(adapter)
    }
    */



}