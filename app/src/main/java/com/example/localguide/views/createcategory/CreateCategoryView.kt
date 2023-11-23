package com.example.localguide.views.createcategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.R
import com.example.localguide.databinding.ActivityCreateCategoryBinding
import com.example.localguide.models.CategoryModel
import com.google.android.material.snackbar.Snackbar

class CreateCategoryView : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCategoryBinding
    var category = CategoryModel()

    private lateinit var presenter: CreateCategoryPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CreateCategoryPresenter(this)

        binding.createCategoryButton.setOnClickListener{
            category.categoryName = binding.createCategoryET.text.toString()
            if (category.categoryName.isEmpty()) {
                Snackbar.make(it,R.string.enter_new_category, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddCategory(category.copy())
                setResult(RESULT_OK)
                finish()
            }
        }

    }

}