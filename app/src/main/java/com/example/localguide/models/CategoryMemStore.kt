package com.example.localguide.models

import timber.log.Timber


var lastCategoryId = 0L

internal fun getIdCategory(): Long {
    return lastId++
}

class CategoryMemStore : CategoryStore {

    val categories = ArrayList<CategoryModel>()
    override fun findAll(): List<CategoryModel> {
        return categories
    }

    override fun create(category: CategoryModel) {
        category.id = getIdCategory()
        categories.add(category)
        logAll()
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

    fun logAll() {
        categories.forEach{ Timber.i("${it}") }
    }
}