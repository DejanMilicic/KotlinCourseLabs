package lab8

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
            .maxByOrNull { it.value }
            ?.key ?: throw NoSuchElementException("No movies found")

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val costarCount = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        movies.forEach { movie ->
            val actors = movie.actors
            for (i in actors.indices) {
                for (j in i + 1 until actors.size) {
                    // Always put actors in alphabetical order within the pair
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