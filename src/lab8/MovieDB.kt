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
        movies.filter { movie -> actor in movie.actors }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> =
        movies
            .sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? =
        getAllMoviesByActor(actor).maxByOrNull { it.rating }

    override fun getAllMoviesByYear(year: Int): List<Movie> =
        movies.filter { it.releaseDate.year == year }

    override fun getAllMoviesByGenre(genre: String): List<Movie> =
        movies.filter { movie -> genre in movie.genres }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> =
        movies
            .sortedByDescending { it.rating }
            .take(numOfMovies)

    override fun getDirectorWithMostMoviesDirected(): MovieDirector =
        movies
            .groupingBy { it.director }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
            ?: throw NoSuchElementException("No movies available.")

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = movies
            .flatMap { movie ->
                val distinctActors = movie.actors.distinct()
                distinctActors.flatMapIndexed { firstIndex, firstActor ->
                    distinctActors
                        .drop(firstIndex + 1)
                        .map { secondActor -> orderedPair(firstActor, secondActor) }
                }
            }
            .groupingBy { it }
            .eachCount()

        val maxCoStarCount = pairCounts.values.maxOrNull() ?: return emptyList()

        return pairCounts
            .filterValues { it == maxCoStarCount }
            .keys
            .sortedWith(compareBy({ it.first.name }, { it.second.name }))
    }

    private fun orderedPair(first: MovieActor, second: MovieActor): Pair<MovieActor, MovieActor> =
        if (first.name <= second.name) first to second else second to first
}