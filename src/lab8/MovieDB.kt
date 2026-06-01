package lab8

class MovieDB (
    private val movies: List<Movie>
): MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> =
        movies
            .filter { movie ->
            actor in movie.actors
        }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> =
        movies
            .sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? =
        movies
            .filter { actor in it.actors }
            .maxByOrNull { it.rating }

    override fun getAllMoviesByYear(year: Int): List<Movie> =
        movies
            .filter { it.releaseDate.year == year }

    override fun getAllMoviesByGenre(genre: String): List<Movie> =
        movies
            .filter {genre in it.genres}

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> =
        movies
            .sortedByDescending { it.rating }
            .take(numOfMovies)

    override fun getDirectorWithMostMoviesDirected(): MovieDirector =
        movies
            .groupingBy { it.director }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key ?: throw NoSuchElementException("There is no movie")

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {

        val pairCounts = movies
            .flatMap { movie ->
                val actors = movie.actors

                val pairs = mutableListOf<Pair<MovieActor, MovieActor>>()

                for (i in actors.indices) {
                    for (j in i + 1 until actors.size) {
                        val sorted = listOf(actors[i], actors[j])
                            .sortedBy { it.name }

                        pairs.add(sorted[0] to sorted[1])
                    }
                }

                pairs
            }
            .groupingBy { it }
            .eachCount()

        val max = pairCounts.maxByOrNull { it.value }?.value ?: 0

        return pairCounts
            .filter { it.value == max }
            .map { it.key }
            .sortedWith(compareBy( { it.first.name }, { it.second.name }))
    }
}