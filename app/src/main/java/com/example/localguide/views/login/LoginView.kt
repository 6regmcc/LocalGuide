package com.example.localguide.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localguide.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import android.util.Log
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
    //private lateinit var auth: FirebaseAuth
    //lateinit var app: MainApp
    private lateinit var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //auth = Firebase.auth
        //app = application as MainApp
        presenter = LoginPresenter(this)
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

                    presenter.signIn(email, password)
                }


            }

        }

        binding.register.setOnClickListener{
            startActivity(Intent(this@LoginView, RegisterView::class.java))
        }


    }






}