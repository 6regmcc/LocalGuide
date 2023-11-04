package com.example.localguide.activities


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem

import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.localguide.R
import com.example.localguide.adapters.ReviewAdapter
import com.example.localguide.adapters.ReviewListener
import com.example.localguide.databinding.ActivityReviewListBinding

import com.example.localguide.main.MainApp
import com.example.localguide.models.ReviewModel
import com.google.android.material.switchmaterial.SwitchMaterial

import timber.log.Timber.Forest.i

class ReviewListActivity : AppCompatActivity(), ReviewListener {
    lateinit var app: MainApp
    private var position: Int = 0
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

        allReviews = app.combinedStore.findAllReviews()
        myReviews = app.combinedStore.findMyReviews()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ReviewAdapter(allReviews,this)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        switch = binding.materialSwitch
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                i("isChecked found ${myReviews.size} reviews")
                binding.recyclerView.adapter = ReviewAdapter(myReviews,this)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            } else {

                i("isNotChecked found ${allReviews.size} reviews")
                binding.recyclerView.adapter = ReviewAdapter(allReviews,this)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)


    }
    override fun onReviewClick(review: ReviewModel, pos : Int) {
        val launcherIntent = Intent(this, ReviewActivity::class.java)
        launcherIntent.putExtra("review_edit", review)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.combinedStore.findAllReviews().size)
            }
            else
                if (it.resultCode == 99)     (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ReviewActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, ReviewMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.combinedStore.findAllReviews().size)
            }
        }


}

