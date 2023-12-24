package com.example.localguide.ui

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.localguide.main.MainApp


import com.example.localguide.models.ReviewDBModel

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.io.File


class ReviewViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()
    //lateinit var app: MainApp
    //private lateinit var reviewTitle: String
    // lateinit var reviewBody: String
    var dbRef: DatabaseReference = FirebaseDatabase.getInstance("https://localguide-402718-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Reviews")
    var auth: FirebaseAuth = Firebase.auth
    val storage = Firebase.storage("gs://localguide-402718.appspot.com")
    var storageRef = storage.reference


    var editedReviewTitle by mutableStateOf("")
        private set

    var editedReviewBody by mutableStateOf("")
        private set

    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    var uploadedImageURL by mutableStateOf("")
        private set


    fun getReviewById(reviewId: String){

        dbRef.child("reviews").child(reviewId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val review  = snapshot.getValue(ReviewDBModel::class.java)
                Timber.i("Found Review ${review} ")
                _uiState.update { currentState ->
                    currentState.copy(reviewFound = true)

                }
                setReviewFields(review)

            }

            override fun onCancelled(error: DatabaseError) {
                Timber.i("Error getting review by id ")
            }
        }
        )
    }



    fun saveReviewToDB() {

        val reviewId: String = dbRef.child("reviews").push().key!!
        val userId = auth.currentUser!!.uid

        val title: String = editedReviewTitle
        val body: String = editedReviewBody
        val imageURL: String = uiState.value.imageURL ?: ""

        val reviewToSave = ReviewDBModel(title = title, userId = userId, body = body, imageURl = imageURL, reviewId = reviewId)
        val childAdd = HashMap<String, Any>()
        childAdd["/reviews/$reviewId"] = reviewToSave
        childAdd["/user-reviews/$userId/$reviewId"] = reviewToSave
        Timber.i("this fun was called./")
        dbRef.updateChildren(childAdd).addOnCompleteListener {
            Timber.i("Review Saved to DB")
            _uiState.update { currentState ->
                currentState.copy(dbRightSuccess = true)

            }
       }.addOnFailureListener{
                err -> Timber.i("DB error is  ${err}")

       }

    }

    fun saveReviewUpdateToDB(reviewId:String) {
        val userId = auth.currentUser!!.uid
        val reviewToSave = ReviewDBModel(title = uiState.value.reviewTitle, body = uiState.value.reviewBody, imageURl = uiState.value.imageURL, reviewId = reviewId, userId = userId)
        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["reviews/$reviewId"] = reviewToSave

        dbRef.updateChildren(childUpdate).addOnCompleteListener {
            Timber.i("Successfully updated review ")
            _uiState.update { currentState ->
                currentState.copy(dbRightSuccess = true)

            }
        }.addOnFailureListener {
            Timber.i("Error saving reivew ")
        }
    }


    private fun setReviewFields(review: ReviewDBModel?) {

        if (review != null) {
            updateReviewTitleState(review.title!!)
            updateReviewBodyState(review.body!!)
            updateReviewImageURL(review.imageURl!!)

        }
        Timber.i("review title ${_uiState.value.reviewTitle}")
        Timber.i("Review Saved to DB ${_uiState.value.reviewBody}")
        Timber.i("Review found ${_uiState.value.reviewFound}")

    }

    fun updateReviewTitleState(reviewTitle: String){
        if(reviewTitle != null) {
            _uiState.update { currentState -> currentState.copy(reviewTitle = reviewTitle) }
        }
    }


    fun updateReviewBodyState(reviewBody: String) {
        if(reviewBody != null) {
            _uiState.update { currentState -> currentState.copy(reviewBody = reviewBody) }
            Timber.i("review body state being updated ${_uiState.value.reviewBody}")
        }

    }

    fun updateReviewImageURL(imageURL: String){
        if(imageURL != null ) {
            _uiState.update { currentState -> currentState.copy(imageURL = imageURL) }
        }
    }

    fun updateReviewTitle(reviewTitleEdited: String) {
        editedReviewTitle = reviewTitleEdited
    }

    fun updateReviewBody(reviewBodyEdited: String) {
        editedReviewBody = reviewBodyEdited
    }

    fun updateImageUri(imageUri: Uri?) {
        selectedImageUri = imageUri
        Timber.i("image URI is $selectedImageUri")
        uploadImageToCloudStorage()
    }

    fun updateDbRightSuccess() {
        _uiState.update { currentState ->
            currentState.copy(dbRightSuccess = false)
        }
    }

    fun showProgressSpinnerUpdate() {
        _uiState.update { currentState -> currentState.copy(showProgressSpinner = true) }
    }

    private fun uploadImageToCloudStorage() {
        var file = selectedImageUri
        val reviewsRef = storageRef.child("images/${file!!.lastPathSegment}")
        val uploadTask = reviewsRef.putFile(file!!)

// Register observers to listen for when the download is done or if it fails

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            reviewsRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Timber.i("image URL is $downloadUri")
                _uiState.update { currentState -> currentState.copy(showProgressSpinner = false)}
                uploadedImageURL = downloadUri.toString()
                _uiState.update { currentState -> currentState.copy(imageURL = uploadedImageURL) }
            } else {
                // Handle failures
                // ...
            }
        }

    }



}