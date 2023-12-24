package com.example.localguide

import ReviewListViewModel
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.localguide.databinding.FragmentLeaderboardBinding
import com.example.localguide.databinding.FragmentReviewListBinding
import com.example.localguide.main.MainApp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn

//import androidx.compose.ui.semantics.SemanticsProperties.Text

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.localguide.models.ReviewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ui.theme.LocalGuideTheme
import androidx.navigation.ui.setupWithNavController
import com.example.localguide.R
import com.example.localguide.activities.Home
import com.google.android.material.navigation.NavigationView
import java.security.AccessController.getContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localguide.models.ReviewDBModel


var review1: ReviewModel = ReviewModel(title="Pips Cafe", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg")
var review2: ReviewModel = ReviewModel(title="Pips Cafe", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg")
var review3: ReviewModel = ReviewModel(title="Pips Cafe", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg")
var review4: ReviewModel = ReviewModel(title="Pips Cafe", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg")
var review5: ReviewModel = ReviewModel(title="Pips Cafe", body="Home of the breakfast roll challenge ", category = "Cafe", rating = 3.0, imageURL = "https://i.postimg.cc/q7pFy6zK/2019-06-27.jpg")
var reviewList: List<ReviewModel> = listOf(review1, review2, review3, review4, review5)



class ReviewListFragment : Fragment() {

    //lateinit var app: MainApp
    private var _binding: FragmentReviewListBinding? = null
    private lateinit var navController: NavController
    private val binding get() = _binding!!


    //val action = ReviewListFragmentDirections.actionReviewListFragmentToReviewFragment()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //app = activity?.application as MainApp


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        //val reviewData: List<ReviewModel> = app.combinedStore.findAllReviews()
        val navController = findNavController()
        //navController.navigate(act)

        _binding = FragmentReviewListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {

                    LocalGuideTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MyApp( navController = navController)
                        }
                    }
            }

        }
        return view

    }


}

fun navigateToReview(navController: NavController, review: ReviewModel) {
    //val action = ReviewListFragmentDirections.actionReviewListFragmentToReviewFragment(review.id)
    //navController.navigate(action)

}

@Composable
private fun ReviewLazyList( modifier: Modifier = Modifier, reviews:List<ReviewDBModel>?, navController: NavController) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = reviews!!) { review ->
            Review(review = review, navController = navController)
        }
    }

}

@Composable
private fun Review(review: ReviewDBModel, navController: NavController, ) {
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
        Row(){
            Column(modifier = Modifier
                .weight(2f)
                .padding(10.dp,)
                .size(75.dp)) {

                var model = review.imageURl ?: ""

                AsyncImage(
                    model = model,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Review Logo",
                    contentScale = ContentScale.Crop,


                )
            }

            Column(modifier = Modifier
                .padding(10.dp,)
                .weight(5f)) {
                if(review.title != null) {
                    Text(text = review.title!!)
                }
                if(review.body != null) {
                    Text(text = review.body!!)
                }


                Button(onClick = {
                    val action = ReviewListFragmentDirections.actionReviewListFragmentToReviewFragment(reviewId = review.reviewId!!,true)
                    navController.navigate(action)
                }) {
                    Text("Full Review")
                }
            }
        }

    }
}
@Composable
private fun MyApp(
    modifier: Modifier = Modifier,
    reviewListViewModel: ReviewListViewModel = viewModel(),
    navController: NavController
) {
    reviewListViewModel.getAllReviewList()
    val reviewListUiState by reviewListViewModel.uiState.collectAsState()
    val reviewList: List<ReviewDBModel>? = reviewListUiState.reviewList?.toList()
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        if(reviewList?.isNotEmpty() == true) {
            ReviewLazyList(reviews = reviewList, navController= navController)
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LocalGuideTheme {
        //MyApp(reviews = reviewList, navController = NavController(context = getApplicationContext())
    }
}
