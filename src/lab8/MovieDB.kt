package lab8

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

internal class MovieDB(private val movies: List<Movie>) : MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { m -> actor in m.actors }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { movie -> movie.revenue - movie.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies.sortedByDescending { m -> m.rating }.filter { m -> actor in m.actors }.firstOrNull()
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { m -> m.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { m -> m.genres.contains(genre) }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { m -> m.rating }.take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupBy { m -> m.director }.maxBy { kv -> kv.value.size }.key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()
        for (movie in movies) {
            val actors = movie.actors.sortedBy { it.name }
            for (i in actors.indices) {
                for (j in (i + 1) until actors.size) {
                    val pair = actors[i] to actors[j]
                    pairCounts[pair] = (pairCounts[pair] ?: 0) + 1
                }
            }
        }
        val maxCount = pairCounts.values.maxOrNull() ?: return emptyList()
        return pairCounts.filter { it.value == maxCount }.keys.toList()
    }

}