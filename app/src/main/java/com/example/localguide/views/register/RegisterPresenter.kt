package com.example.localguide.views.register

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.localguide.activities.LoginActivity
import com.example.localguide.main.MainApp
import com.example.localguide.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import timber.log.Timber

class RegisterPresenter (val view: RegisterView) {
    private lateinit var auth: FirebaseAuth
    var app: MainApp
    private lateinit var loginIntentLauncher: ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        auth = Firebase.auth
        registerLoginIntentLauncher()
    }

    fun doRegisterUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(view) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.i("EmailPassword", "createUserWithEmail:success")

                    val firebaseUser = auth.currentUser
                    Timber.i("User id is ${firebaseUser?.uid}")
                    Timber.i("User email is ${firebaseUser?.email}")
                    val userToSave = UserModel()
                    userToSave.userName = name
                    if (firebaseUser != null) {
                        userToSave.userId = firebaseUser.uid
                    }
                    if (firebaseUser != null) {
                        userToSave.userEmailAddress = firebaseUser.email.toString()
                    }

                    app.combinedStore.createUser(userToSave)
                    Toast.makeText(
                        view,
                        "Account Registered Successfully.",
                        Toast.LENGTH_SHORT,
                    ).show()

                    updateUI(firebaseUser)


                } else {
                    // If sign in fails, display a message to the user.
                    Timber.i("EmailPassword", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        view,
                        "Signup failed. ${task.exception?.message}",
                        Toast.LENGTH_LONG,
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun updateUI(user: FirebaseUser?) {
        val intent = Intent(view, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", user?.uid)
        intent.putExtra("email", user?.email)

        loginIntentLauncher.launch(intent)
        view.finish()
    }


    fun registerLoginIntentLauncher() {
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }



}