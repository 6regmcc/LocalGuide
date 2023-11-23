package com.example.localguide.views.createcategory

import com.example.localguide.main.MainApp
import com.example.localguide.models.CategoryModel


class CreateCategoryPresenter (private val view: CreateCategoryView) {

    var category = CategoryModel()
    var app: MainApp = view.application as MainApp
    fun doAddCategory(category: CategoryModel) {
        app.combinedStore.createCategory(category)
    }
}