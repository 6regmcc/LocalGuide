package com.example.localguide.models

interface CategoryStore {
    fun findAll(): List<CategoryModel>
    fun create(category: CategoryModel)

}