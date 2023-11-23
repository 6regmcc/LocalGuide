package com.example.localguide.views.reviewlist


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.localguide.R
import com.example.localguide.adapters.ReviewAdapter
import com.example.localguide.adapters.ReviewListener
import com.example.localguide.databinding.ActivityReviewListBinding

import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.material.switchmaterial.SwitchMaterial

import timber.log.Timber.Forest.i

class ReviewListView : AppCompatActivity(), ReviewListener {
    lateinit var app: MainApp
    private var position: Int = 0
    lateinit var presenter: ReviewListPresenter
    lateinit var allReviews: List<ReviewModel>
    lateinit var myReviews: List<ReviewModel>
    lateinit var switch: SwitchMaterial
    lateinit var adapterArray: List<ReviewModel>
    var showMyReviewSwitchState = false

    private lateinit var binding: ActivityReviewListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        presenter = ReviewListPresenter(this)
        //myReviews = presenter.getFilteredReviews()
        //allReviews = presenter.getReviews()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadReviews()

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)



        switch = binding.materialSwitch
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                i("isChecked found ${presenter.getFilteredReviews().size} reviews")
                loadFilteredReviews()
                //binding.recyclerView.adapter?.notifyDataSetChanged()
            } else {

                i("isNotChecked found ${presenter.getReviews().size} reviews")
                loadReviews()

            }

        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddReview() }
            R.id.item_map -> { presenter.doShowReviewsMap() }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onReviewClick(review: ReviewModel, pos : Int) {
        this.position = position
        presenter.doEditReview(review, this.position)
    }

   private fun loadReviews() {
       binding.recyclerView.adapter = ReviewAdapter(presenter.getReviews(), this)
       onRefresh()
   }

    private fun loadFilteredReviews() {
        binding.recyclerView.adapter = ReviewAdapter(presenter.getFilteredReviews(),this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getReviews().size)
    }


    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }




}

