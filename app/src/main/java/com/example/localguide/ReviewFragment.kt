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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
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
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
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
                        ReviewScreen()
                    }
                }
            }

        }
        return view
    }


}


@Composable
fun ReviewScreen(
    reviewViewModel: ReviewViewModel = viewModel()
){
    val reviewUiState by reviewViewModel.uiState.collectAsState()
    ReviewFields(
        onReviewTitleChanged = {reviewViewModel.updateReviewTitle(it)},
        onKeyboardDone = { },
        editedReviewTitle =  reviewViewModel.editedReviewTitle
    )
}

@Composable
fun ReviewFields(
    onReviewTitleChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    editedReviewTitle: String,
) {
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
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LocalGuideTheme {

        ReviewScreen()
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