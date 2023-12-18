package com.example.localguide.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken

import com.example.localguide.helpers.exists
import com.example.localguide.helpers.read
import com.example.localguide.helpers.write
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

import timber.log.Timber
import java.lang.reflect.Type
import java.util.Random

const val JSON_FILE = "localGuide.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<JSONModel>() {}.type

fun generateRandomId2(): Long {
    return Random().nextLong()
}

class CombinedJSONStore (
    private val context: Context): JSONStore {

     var combinedJSonObj: JSONModel = JSONModel()
     var auth: FirebaseAuth = Firebase.auth


    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }

    }

    override fun findAllCategories(): List<CategoryModel> {
        //logAll()
        return combinedJSonObj.categories
    }


    override fun createCategory(category: CategoryModel) {
        category.id = generateRandomId2()
        combinedJSonObj.categories.add(category)
        serialize()
    }

    override fun findById(id: Long): CategoryModel? {
        TODO("Not yet implemented")
    }

    override fun getStrArrOfCategories(): MutableList<String> {
        val categoryStrArray = mutableListOf<String>()
        for (category in combinedJSonObj.categories){
            categoryStrArray.add(category.categoryName)
        }
        return categoryStrArray
    }

    override fun findAllReviews(): List<ReviewModel> {
        logAllReviews()
        return combinedJSonObj.reviews
    }

    override fun findMyReviews(): List<ReviewModel> {
       return findAllReviews().filter {it.userId.equals(getCurrentUserId())}

    }

    override fun findReviewById(id: Long): ReviewModel {
       val foundReview: ReviewModel = combinedJSonObj.reviews.find { it.id == id }!!
        return foundReview
    }

    override fun createUser(user: UserModel) {
        combinedJSonObj.users.add(user)
    }

    override fun getCurrentUserId(): String {
        val user = Firebase.auth.currentUser
        if (user != null) {
            return user.uid
        } else {
            return "no id"
        }
    }


    override fun updateReview(review: ReviewModel) {
        val reviewsList = findAllReviews() as ArrayList<ReviewModel>
        var foundReview: ReviewModel? = reviewsList.find { p -> p.id == review.id }
        if (foundReview != null) {
            foundReview.title = review.title
            foundReview.body = review.body
            foundReview.image = review.image
            foundReview.lat = review.lat
            foundReview.lng = review.lng
            foundReview.zoom = review.zoom
            foundReview.rating = review.rating
            foundReview.category = review.category
        }
        serialize()
    }

    override fun deleteReview(review: ReviewModel) {
        combinedJSonObj.reviews.remove(review)
        serialize()
    }



    private fun logAllReviews() {
        combinedJSonObj.reviews.forEach { Timber.i("$it") }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(combinedJSonObj, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        combinedJSonObj = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun createReview(review: ReviewModel) {
        review.id = generateRandomId2()
        combinedJSonObj.reviews.add(review)
        serialize()
    }






}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }




}