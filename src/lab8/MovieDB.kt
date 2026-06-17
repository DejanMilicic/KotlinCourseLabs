package lab8

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

class MovieDB(val movies: List<Movie>) : MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { it.actors.contains(actor) }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies.filter { it.actors.contains(actor) }.sortedByDescending { it.rating }.first()
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { it.genres.contains(genre) }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.rating }.take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupBy { it.director }.mapValues { it.value.size }.maxBy { it.value }.key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val costarredMovies = movies.flatMap { movie ->
            movie.actors.flatMapIndexed { index, actor1 ->
                movie.actors.drop(index + 1)
                    .map { actor2 -> if (actor1.name < actor2.name) actor1 to actor2 else actor2 to actor1 }
            }
        }
            .groupBy { it }
            .mapValues { it.value.size }

        val max = costarredMovies.maxOf { it.value }
        return costarredMovies.filter { it.value == max }.keys.toList()
    }

}