import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getTrending() = performFetchingAndSaving(
        { localDataSource.getAll() },
        { remoteDataSource.getTrending() },
        { localDataSource.insertMovies(it.results) }
    )
}