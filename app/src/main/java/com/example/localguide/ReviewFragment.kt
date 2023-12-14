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
                        MyApp(text = args.reviewId.toString())
                    }
                }
            }

        }
        return view
    }


}

@Composable
fun MyApp(text: String){
    Text(text = text )
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LocalGuideTheme {

        MyApp(text = "preview")
    }
}
