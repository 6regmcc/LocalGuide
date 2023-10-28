package com.example.localguide.models

class CategoryMemStore : CategoryStore {

    val categories = ArrayList<CategoryModel>()
    override fun findAll(): List<CategoryModel> {
        return categories
    }

    override fun create(category: CategoryModel) {
        TODO("Not yet implemented")
    }
}