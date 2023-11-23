package com.example.localguide.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import android.widget.Toast
import com.example.localguide.main.MainApp
import com.example.localguide.views.register.RegisterView
import com.example.localguide.views.reviewlist.ReviewListView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import timber.log.Timber.Forest.i

class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        app = application as MainApp

        val email = intent.getStringExtra("email")
        binding.userEmailInput.setText(email)

        binding.loginButton.setOnClickListener{
            when {

                TextUtils.isEmpty(binding.userEmailInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginView,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.userPasswordInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginView,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val email: String = binding.userEmailInput.text.toString().trim {it <= ' '}
                    val password: String = binding.userPasswordInput.text.toString().trim {it <= ' '}

                    signIn(email, password)
                }


            }

        }

        binding.register.setOnClickListener{
            startActivity(Intent(this@LoginView, RegisterView::class.java))
        }


    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    i(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    i("User id is ${user?.uid}")
                    i("User email is ${user?.email}")

                    Toast.makeText(
                        baseContext,
                        "Sign successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    i(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@LoginView, ReviewListView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", user?.uid)
        intent.putExtra("email", user?.email)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}