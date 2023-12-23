import androidx.lifecycle.ViewModel
import com.example.localguide.models.ReviewDBModel
import com.example.localguide.ui.ReviewListUiState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class ReviewListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewListUiState())
    val uiState: StateFlow<ReviewListUiState> = _uiState.asStateFlow()
    var dbRef: DatabaseReference = FirebaseDatabase.getInstance("https://localguide-402718-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Reviews")

    fun getAllReviewList() {
        Timber.i("get all reviews called")

        dbRef.child("reviews").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewList: MutableList<ReviewDBModel> = mutableListOf()
                Timber.i("functgion getting tghis far ")
                if(snapshot.exists()){
                    for(review in snapshot.children) {
                        val reviewData = review.getValue(ReviewDBModel::class.java)
                        Timber.i("got review from DB $reviewData")
                        reviewList.add(reviewData!!)
                        _uiState.update { currentState -> currentState.copy(reviewList = reviewList)}
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Timber.i("db error $error")
            }
        })
    }

}