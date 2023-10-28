package com.example.localguide.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken

import com.example.localguide.helpers.exists
import com.example.localguide.helpers.read
import com.example.localguide.helpers.write

import timber.log.Timber
import java.lang.reflect.Type
import java.util.Random

const val NEW_JSON_FILE = "localGuide.json"
val gsonBuilder2: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType2: Type = object : TypeToken<JSONModel>() {}.type

fun generateRandomId2(): Long {
    return Random().nextLong()
}

class CombinedJSONStore (private val context: Context): JSONStore {

     lateinit var combinedJSonObj: JSONModel

    init {
        if (exists(context, NEW_JSON_FILE)) {
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

    override fun createReview(review: ReviewModel) {
        review.id = generateRandomId2()
        combinedJSonObj.reviews.add(review)
    }


    override fun findReviewById(id: Long): ReviewModel? {
       val foundReview: ReviewModel? = combinedJSonObj.reviews.find { it.id == id }
        return foundReview
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
        val jsonString = gsonBuilder2.toJson(combinedJSonObj, listType2)
        write(context, NEW_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, NEW_JSON_FILE)
        combinedJSonObj = gsonBuilder2.fromJson(jsonString, listType2)
    }






}

class UriParser2 : JsonDeserializer<Uri>,JsonSerializer<Uri> {
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