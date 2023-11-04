package com.example.localguide.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import com.example.localguide.databinding.ActivityRegisterBinding
import com.example.localguide.main.MainApp
import com.example.localguide.models.UserModel


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import timber.log.Timber.Forest.i


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        app = application as MainApp

        binding.registerLoginButton.setOnClickListener{

            when {
                TextUtils.isEmpty(binding.registerUserName.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.registerUserEmailInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.registerUserPasswordInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val email: String = binding.registerUserEmailInput.text.toString().trim {it <= ' '}
                    val password: String = binding.registerUserPasswordInput.text.toString().trim {it <= ' '}
                    val name: String = binding.registerUserName.text.toString().trim {it <= ' '}
                    createAccount(email, password, name)
                }


            }

        }

        binding.signIn.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

        }
    }


    private fun createAccount(email: String, password: String, name: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    i(TAG, "createUserWithEmail:success")

                    val firebaseUser = auth.currentUser
                    i("User id is ${firebaseUser?.uid}")
                    i("User email is ${firebaseUser?.email}")
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
                        baseContext,
                        "Account Registered Successfully.",
                        Toast.LENGTH_SHORT,
                    ).show()

                    updateUI(firebaseUser)


                } else {
                    // If sign in fails, display a message to the user.
                    i(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Signup failed. ${task.exception?.message}",
                        Toast.LENGTH_LONG,
                    ).show()
                    //updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
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

