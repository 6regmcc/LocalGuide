package com.example.localguide

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
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.Context
import androidx.compose.foundation.layout.Column
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
import com.example.localguide.models.ReviewModel
import com.example.localguide.ui.ReviewViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState



var test = "test"

class ReviewFragment : Fragment() {

    lateinit var app: MainApp
    private var _binding: FragmentReviewBinding? = null
    private lateinit var navController: NavController
    private val binding get() = _binding!!
    private val args by navArgs<ReviewFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val view = binding.root
        val navController = findNavController()
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
                        ReviewScreen(navController = navController)

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
    navController: NavController?
){
    val reviewUiState by reviewViewModel.uiState.collectAsState()
    Column (Modifier.padding(16.dp)){
        ReviewFields(
            onReviewTitleChanged = {reviewViewModel.updateReviewTitle(it)},
            onReviewDbSubmit = {reviewViewModel.updateDbRightSuccess() },
            onKeyboardDone = { },
            editedReviewTitle = reviewViewModel.editedReviewTitle!!,
            dbRightSuccess = reviewUiState.dbRightSuccess,
            navController = navController!!,
            onReviewBodyChanged = {reviewViewModel.updateReviewBody(it)},
            editedReviewBody = reviewViewModel.editedReviewBody!!,
            showProgressSpinner = reviewUiState.showProgressSpinner

        )

    }



}

@Composable
fun ReviewFields(
    reviewViewModel: ReviewViewModel = viewModel(),
    onReviewTitleChanged: (String) -> Unit,
    onReviewBodyChanged: (String) -> Unit,
    onReviewDbSubmit: () -> Unit,

    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    editedReviewTitle: String,
    editedReviewBody: String,
    dbRightSuccess: Boolean,
    context: Context = LocalContext.current,
    navController: NavController,
    showProgressSpinner: Boolean
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
        Text(text = "test")
        OutlinedTextField(
            value = editedReviewTitle,
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
        OutlinedTextField(value = editedReviewBody, onValueChange = onReviewBodyChanged)
        Text(text = "test")

        Button(modifier = Modifier.fillMaxWidth(),onClick = {}) {
            Text(
                text = "test button",
                fontSize = 16.sp
            )
        }


        Button(
            modifier = Modifier.fillMaxWidth(),

            onClick = {reviewViewModel.saveReviewToDB()

            }
        ) {
            Text(
                text = "Save Review",
                fontSize = 16.sp
            )
        }
        ImagePicker(
            onReviewImageSelected = {reviewViewModel.updateImageUri(it)},
            //uploadImageToCloudStorage = {reviewViewModel.uploadImageToCloudStorage()}
            showProgressSpinner = showProgressSpinner,
            showProgressSpinnerUpdate = {reviewViewModel.showProgressSpinnerUpdate()}

        )
    }

}


@Composable
private fun ImagePicker(
    modifier: Modifier = Modifier,
    onReviewImageSelected: (Uri?) -> Unit,
    showProgressSpinner: Boolean,
    showProgressSpinnerUpdate: () -> Unit


) {



    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onReviewImageSelected(uri)
    }
    Column() {
        Button(onClick = {
            launcher.launch("image/*")
            showProgressSpinnerUpdate()




        }) {
            Text(text = "Pick image")
        }

        if (showProgressSpinner) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
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

/*
@Composable
private fun ComposeGoogleMap() {
    var location = LatLng(53.32754351898156, -6.304286867380142,)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 16f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        val markerState = rememberMarkerState(position = location)
        Marker(
            state = markerState,
            draggable = true
        )

    }

}


 */