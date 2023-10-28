package com.example.localguide.models

class CategoryMemStore : CategoryStore {

    val categories = ArrayList<CategoryModel>()
    override fun findAll(): List<CategoryModel> {
        return categories
    }

    override fun create(category: CategoryModel) {
        TODO("Not yet implemented")
    }

    override fun update(category: CategoryModel) {
        TODO("Not yet implemented")
    }

    override fun getStringArray(): MutableList<String> {
        val stringList = mutableListOf<String>()
        for (category in categories) {
            stringList.add(category.categoryName)
        }

        return stringList
    }
}