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
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { actor in it.actors }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies.filter { actor in it.actors }.maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { genre in it.genres }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.rating }.take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupBy { it.director }.maxBy { it.value.size }.key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCount = hashMapOf<Pair<MovieActor, MovieActor>, Int>()
        for (movie in movies) {
            val actors = movie.actors.distinct().sortedBy { it.name }
            for ((i, _) in actors.withIndex()) {
                for (j in (i + 1)..<actors.size) {
                    val pair = Pair(actors[i], actors[j])
                    pairCount[pair] = (pairCount[pair] ?: 0) + 1
                }
            }
        }

        return pairCount.filter { it.value == pairCount.values.max() }
            .keys.toList().sortedBy { it.first.name }
            .sortedWith(compareBy({ it.first.name }, { it.second.name }))
    }
}