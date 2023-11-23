package com.example.localguide.views.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.localguide.activities.LoginActivity

import com.example.localguide.databinding.ActivityRegisterBinding


import com.google.firebase.auth.FirebaseUser


class RegisterView : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    //private lateinit var auth: FirebaseAuth
    //lateinit var app: MainApp
    private lateinit var presenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //auth = Firebase.auth

        //app = application as MainApp
        presenter = RegisterPresenter(this)
        binding.registerLoginButton.setOnClickListener{

            when {
                TextUtils.isEmpty(binding.registerUserName.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterView,
                        "Please enter your name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.registerUserEmailInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterView,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.registerUserPasswordInput.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterView,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val email: String = binding.registerUserEmailInput.text.toString().trim {it <= ' '}
                    val password: String = binding.registerUserPasswordInput.text.toString().trim {it <= ' '}
                    val name: String = binding.registerUserName.text.toString().trim {it <= ' '}
                    presenter.doRegisterUser(email, password, name)
                }


            }

        }

        binding.signIn.setOnClickListener{
            startActivity(Intent(this@RegisterView, LoginActivity::class.java))

        }
    }




    fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@RegisterView, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", user?.uid)
        intent.putExtra("email", user?.email)
        startActivity(intent)
        finish()
    }





}

