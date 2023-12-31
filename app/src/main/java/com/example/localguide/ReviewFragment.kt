package com.example.localguide

import ReviewListViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.example.localguide.databinding.FragmentReviewBinding
import com.example.localguide.databinding.FragmentReviewListBinding
import com.example.localguide.main.MainApp
import com.example.myapplication.ui.theme.LocalGuideTheme
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.example.localguide.models.ReviewDBModel
import com.example.localguide.ui.ReviewUiState

import com.example.localguide.ui.ReviewViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState




class ReviewFragment : Fragment() {


    private var _binding: FragmentReviewBinding? = null
    private lateinit var navController: NavController

    private val binding get() = _binding!!
    private val args by navArgs<ReviewFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()
        binding.composeView.apply {

            val reviewId: String
            if (args.reviewId != null ) {
                reviewId = args.reviewId.toString()
            } else {
                reviewId = ""
            }
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                LocalGuideTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ReviewScreen(navController = navController, reviewId = reviewId, isEdit = args.isEdit)

                    }
                }
            }

        }
        return view
    }


}


@Composable
fun ReviewScreen(
    reviewViewModel: ReviewViewModel = viewModel(),
    navController: NavController?,
    reviewId: String? = "",
    isEdit: Boolean

){
    val reviewUiState by reviewViewModel.uiState.collectAsState()
    if(!reviewUiState.reviewFound && isEdit) {
       reviewViewModel.getReviewById(reviewId!!)
    }

    Column (Modifier.padding(16.dp)){
        if(reviewUiState.reviewFound) {
            ReviewFields(
                onReviewTitleChanged = {reviewViewModel.updateReviewTitleState(it)},
                onReviewDbSubmit = {reviewViewModel.updateDbRightSuccess() },
                onKeyboardDone = { },
                editedReviewTitle = reviewUiState.reviewTitle,
                dbRightSuccess = reviewUiState.dbRightSuccess,
                navController = navController!!,
                onReviewBodyChanged = {reviewViewModel.updateReviewBodyState(it)},
                editedReviewBody = reviewUiState.reviewBody,
                showProgressSpinner = reviewUiState.showProgressSpinner,
                reviewFound = reviewUiState.reviewFound,
                reviewId = reviewId,
                isFieldEnabled = reviewUiState.isLoggedInUserIsOwnerOfReview,
                latitude = reviewUiState.latitude,
                longitude = reviewUiState.longitude,
                isLocationValue = reviewUiState.isLocationValue,
                reviewUiState = reviewUiState



            )
        } else {
            ReviewFields(
                onReviewTitleChanged = {reviewViewModel.updateReviewTitle(it)},
                onReviewDbSubmit = {reviewViewModel.updateDbRightSuccess() },
                onKeyboardDone = { },
                editedReviewTitle = reviewViewModel.editedReviewTitle!!,
                dbRightSuccess = reviewUiState.dbRightSuccess,
                navController = navController!!,
                onReviewBodyChanged = {reviewViewModel.updateReviewBody(it)},
                editedReviewBody = reviewViewModel.editedReviewBody!!,
                showProgressSpinner = reviewUiState.showProgressSpinner,
                reviewFound = reviewUiState.reviewFound,
                isFieldEnabled = true,
                latitude = reviewUiState.latitude,
                longitude = reviewUiState.longitude,
                isLocationValue = reviewUiState.isLocationValue,
                reviewUiState = reviewUiState

            )
        }
    }



}

@Composable
fun ReviewFields(
    reviewViewModel: ReviewViewModel = viewModel(),
    onReviewTitleChanged: (String) -> Unit,
    onReviewBodyChanged: (String) -> Unit,
    onReviewDbSubmit: () -> Unit,
    reviewUiState: ReviewUiState,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    editedReviewTitle: String,
    editedReviewBody: String,
    dbRightSuccess: Boolean,
    context: Context = LocalContext.current,
    navController: NavController,
    showProgressSpinner: Boolean,
    reviewFound: Boolean,
    reviewId: String? = "",
    isFieldEnabled: Boolean,
    latitude: String?,
    longitude: String?,
    isLocationValue: Boolean

) {

    if(dbRightSuccess) {

        Toast.makeText(context,
            "Review Saved to DB",
            Toast.LENGTH_LONG).show()
        val action = ReviewFragmentDirections.actionReviewFragmentToReviewListFragment()
        onReviewDbSubmit()
        navController.navigate(action)



    }
    Column (Modifier.padding(16.dp)) {

        OutlinedTextField(
            enabled = isFieldEnabled,
            value = editedReviewTitle,
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onReviewTitleChanged,
            //label = "review title",
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            ),
        )
        OutlinedTextField(enabled = isFieldEnabled, value = editedReviewBody, onValueChange = onReviewBodyChanged,  modifier = Modifier.fillMaxWidth(), label = { Text("Description") },)




        if(reviewFound && isFieldEnabled) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !showProgressSpinner,
                onClick = {reviewViewModel.saveReviewUpdateToDB(reviewId!!)

                }
            ) {
                Text(
                    text = "Update Review",
                    fontSize = 16.sp
                )
            }
        } else if(reviewFound) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                onClick = {reviewViewModel.saveReviewUpdateToDB(reviewId!!)

                }
            ) {
                Text(
                    text = "Update Review",
                    fontSize = 16.sp
                )
            }
        }else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !showProgressSpinner,
                onClick = {reviewViewModel.saveReviewToDB()

                }
            ) {
                Text(
                    text = "Save Review",
                    fontSize = 16.sp
                )
            }
        }


        ImagePicker(

            onReviewImageSelected = {reviewViewModel.updateImageUri(it)},
            //uploadImageToCloudStorage = {reviewViewModel.uploadImageToCloudStorage()}
            showProgressSpinner = showProgressSpinner,
            showProgressSpinnerUpdate = {reviewViewModel.showProgressSpinnerUpdate()},
            isFieldEnabled = isFieldEnabled

        )

        ComposeGoogleMap(
            updateLatLon = {reviewViewModel.updateLatLon(it)},
            latitude = latitude,
            longitude = longitude,
            isLocationValue = isLocationValue,
            reviewUiState = reviewUiState
            )
    }

}


@Composable
private fun ImagePicker(
    modifier: Modifier = Modifier,
    onReviewImageSelected: (Uri?) -> Unit,
    showProgressSpinner: Boolean,
    showProgressSpinnerUpdate: () -> Unit,
    isFieldEnabled: Boolean


) {



    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onReviewImageSelected(uri)
    }
    Row() {
        Button(enabled = isFieldEnabled,onClick = {

            launcher.launch("image/*")
            showProgressSpinnerUpdate()

        }) {
            Text(text = "Pick image")
        }
        if (showProgressSpinner) {
            CircularProgressIndicator(
                modifier = Modifier.width(45.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }


    }
}
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LocalGuideTheme {

        //ReviewScreen(navController = null)
    }
}


@Composable
private fun ComposeGoogleMap(
    updateLatLon: (LatLng) -> Unit,
    latitude: String?,
    longitude: String?,
    isLocationValue: Boolean,
    reviewUiState: ReviewUiState

) {

    var location = LatLng(53.32754351898156, -6.304286867380142,)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 16f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = updateLatLon,
    ) {
        if(isLocationValue) {

            val updatedLocation = LatLng(reviewUiState.latitude?.toDouble()!!, reviewUiState.longitude?.toDouble()!! )

            println("marker re-running and isLocationValue = $isLocationValue and lat log is $updatedLocation")
            val markerState = MarkerState(position = updatedLocation)

            Marker(
                state = markerState,
                draggable = true
            )

        }

    }

}


