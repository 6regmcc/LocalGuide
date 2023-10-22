package com.example.localguide.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.localguide.databinding.ActivityReviewMapsBinding
class ReviewMapsActivity : AppCompatActivity(){
    private lateinit var binding: ActivityReviewMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }
}