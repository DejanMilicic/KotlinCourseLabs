package lab8

class MovieDB(val movies: List<Movie>) : MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { actor in it.actors }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return getAllMoviesByActor(actor).maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { genre in it.genres }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.rating }
            .take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupingBy { it.director }
            .eachCount()
            .maxBy { it.value }
            .key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairs = mutableListOf<Pair<MovieActor, MovieActor>>()

        movies.forEach { movie ->
            for (i in movie.actors.indices) {
                for (j in i + 1 until movie.actors.size) {
                    pairs.add(movie.actors[i] to movie.actors[j])
                }
            }
        }

        val counts = pairs.groupingBy { it }.eachCount()
        val maxCount = counts.maxOf { it.value }

        return counts.filter { it.value == maxCount }
            .map { it.key }
    }
}

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */
