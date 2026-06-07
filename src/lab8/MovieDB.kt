package lab8

class MovieDB (
    private val movies: List<Movie>
): MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> =
        movies.filter { actor in it.actors }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        require(numOfMovies >= 0)

        return movies
            .sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? =
        movies
            .filter { actor in it.actors }
            .maxByOrNull { it.rating }

    override fun getAllMoviesByYear(year: Int): List<Movie> =
        movies.filter { it.releaseDate.year == year }

    override fun getAllMoviesByGenre(genre: String): List<Movie> =
        movies.filter {genre in it.genres}

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        require(numOfMovies >= 0)

        return movies
            .sortedByDescending { it.rating }
            .take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector =
        movies
            .groupingBy { it.director }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key ?: throw NoSuchElementException("There is no movie")

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = movies
            .flatMap { movie ->
                movie.actors.flatMapIndexed { index, actor ->
                    movie.actors
                        .drop(index + 1)
                        .map { otherActor ->
                            listOf(actor, otherActor)
                                .sortedBy { it.name }
                                .let { it[0] to it[1] }
                        }
                }
            }
            .groupingBy { it }
            .eachCount()

        val maxCount = pairCounts.maxOfOrNull { it.value } ?: 0

        return pairCounts
            .filter { it.value == maxCount }
            .keys
            .sortedWith(compareBy<Pair<MovieActor, MovieActor>> { it.first.name }
                .thenBy { it.second.name }
            )
    }
}