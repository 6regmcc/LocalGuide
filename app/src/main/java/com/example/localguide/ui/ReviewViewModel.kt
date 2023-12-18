package com.example.localguide.ui

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.localguide.main.MainApp
import com.example.localguide.models.CombinedJSONStore

import com.example.localguide.models.ReviewDBModel
import com.example.localguide.models.ReviewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class ReviewViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()
    lateinit var app: MainApp
    private lateinit var reviewTitle: String
    private lateinit var reviewBody: String
    var dbRef: DatabaseReference = FirebaseDatabase.getInstance("https://localguide-402718-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Reviews")
    var auth: FirebaseAuth = Firebase.auth

    //val exampleReview: ReviewModel = ReviewModel(title="", body="", category = "", rating = 0.0, imageURL = "")

    //val currentReview: ReviewModel = getCurrentReview(4217002262694485753)

    var editedReviewTitle by mutableStateOf("")
        private set

    var editedReviewBody by mutableStateOf("")
        private set





    fun saveReviewToDB() {

        val reviewId: String = dbRef.child("reviews").push().key!!
        val userId = auth.currentUser!!.uid

        val title: String = editedReviewTitle
        val body: String = editedReviewBody

        val reviewToSave = ReviewDBModel(title = title, userId = userId, body = body)
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

    fun updateReviewTitle(reviewTitleEdited: String) {
        editedReviewTitle = reviewTitleEdited
    }
    fun updateReviewBody(reviewBodyEdited: String) {
        editedReviewBody = reviewBodyEdited
    }

    fun updateDbRightSuccess() {
        _uiState.update { currentState ->
            currentState.copy(dbRightSuccess = false)
        }
    }



}