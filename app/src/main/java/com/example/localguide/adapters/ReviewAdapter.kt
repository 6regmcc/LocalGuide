package com.example.localguide.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.databinding.CardReviewBinding
import com.example.localguide.models.ReviewModel

class ReviewAdapter constructor(private var reviews: List<ReviewModel>) :
    RecyclerView.Adapter<ReviewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardReviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val review = reviews[holder.adapterPosition]
        holder.bind(review)
    }

    override fun getItemCount(): Int = reviews.size

    class MainHolder(private val binding : CardReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: ReviewModel) {
            binding.reviewTitle.text = review.title
            binding.reviewtextBody.text = review.body
        }
    }
}