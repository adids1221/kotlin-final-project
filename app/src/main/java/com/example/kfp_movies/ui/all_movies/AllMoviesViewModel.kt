
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    val movies = movieRepository.getTrending()
}