package com.example.localguide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.R
import com.example.localguide.databinding.ActivityReviewListBinding
import com.example.localguide.databinding.CardReviewBinding
import com.example.localguide.models.ReviewModel
import com.example.localguide.main.MainApp

class ReviewListActivity : AppCompatActivity() {
    lateinit var app: MainApp


    private lateinit var binding: ActivityReviewListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ReviewAdapter(app.reviews)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


}

class ReviewAdapter constructor(private var placemarks: List<ReviewModel>) :
    RecyclerView.Adapter<ReviewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardReviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark)
    }



    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding : CardReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewModel) {
            binding.reviewTitle.text = review.title
            binding.reviewtextBody.text = review.body

        }
    }
}