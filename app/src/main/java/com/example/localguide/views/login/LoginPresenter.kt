package com.example.localguide.views.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.localguide.activities.Home
import com.example.localguide.views.reviewlist.ReviewListView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import timber.log.Timber

class LoginPresenter (var view: LoginView) {

    var auth: FirebaseAuth = Firebase.auth
    lateinit var reviewListIntentLauncher: ActivityResultLauncher<Intent>

    init {

        registerReviewIntentLauncher()

    }

    fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(view) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.i("tag_login", "signInWithEmail:success")
                    val user = auth.currentUser
                    Timber.i("User id is ${user?.uid}")
                    Timber.i("User email is ${user?.email}")

                    Toast.makeText(
                        view,
                        "Sign successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.i("tag_login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        view,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    fun updateUI(user: FirebaseUser?) {
        //val intent = Intent(view, ReviewListView::class.java)
        val intent = Intent(view, Home::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", user?.uid)
        intent.putExtra("email", user?.email)
        reviewListIntentLauncher.launch(intent)
        view.finish()
    }

    fun registerReviewIntentLauncher() {
        reviewListIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}