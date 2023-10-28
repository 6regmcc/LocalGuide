package com.example.localguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.R
import com.example.localguide.databinding.ActivityCreateCategoryBinding
import com.example.localguide.databinding.ActivityReviewBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.CategoryModel
import com.google.android.material.snackbar.Snackbar

class CreateCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCategoryBinding
    var category = CategoryModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        binding.createCategoryButton.setOnClickListener{
            category.categoryName = binding.createCategoryET.text.toString()
            if (category.categoryName.isEmpty()) {
                Snackbar.make(it,R.string.enter_new_category, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                app.combinedStore.createCategory(category.copy())
                setResult(RESULT_OK)
                finish()
            }
        }

    }

}