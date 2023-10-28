package com.example.localguide.models

interface JSONStore {

    fun findAllCategories(): List<CategoryModel>
    fun createCategory(category: CategoryModel)
    fun findById(id:Long) : CategoryModel?
    fun getStrArrOfCategories(): MutableList<String>
}