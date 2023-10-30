package com.example.localguide.models

class JSONModel (val categories: MutableList<CategoryModel> = mutableListOf(),
                 val users: MutableList<UserModel> = mutableListOf(),
                 val reviews: MutableList<ReviewModel> = mutableListOf())