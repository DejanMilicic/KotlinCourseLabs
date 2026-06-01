package lab8

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

class MovieDB(private val movies: List<Movie>) : MovieDBApi {

    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> =
        movies.filter { actor in it.actors }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> =
        movies.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? =
        getAllMoviesByActor(actor).maxByOrNull { it.rating }

    override fun getAllMoviesByYear(year: Int): List<Movie> =
        movies.filter { it.releaseDate.year == year }

    override fun getAllMoviesByGenre(genre: String): List<Movie> =
        movies.filter { genre in it.genres }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> =
        movies.sortedByDescending { it.rating }.take(numOfMovies)

    override fun getDirectorWithMostMoviesDirected(): MovieDirector =
        movies.groupingBy { it.director }
            .eachCount()
            .maxBy { it.value }
            .key

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val costarCount = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        movies.forEach { movie ->
            val actors = movie.actors
            for (i in actors.indices) {
                for (j in i + 1 until actors.size) {
                    // Uvek stavi aktore u abecednom redu unutar para
                    val a = actors[i]
                    val b = actors[j]
                    val pair = if (a.name < b.name) Pair(a, b) else Pair(b, a)
                    costarCount[pair] = (costarCount[pair] ?: 0) + 1
                }
            }
        }

        val maxCount = costarCount.values.maxOrNull() ?: return emptyList()
        return costarCount
            .filter { it.value == maxCount }
            .keys
            .sortedWith(compareBy({ it.first.name }, { it.second.name }))
    }
}