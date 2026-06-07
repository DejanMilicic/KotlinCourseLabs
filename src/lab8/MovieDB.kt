package lab8

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

class MovieDB(val movies : List<Movie>) : MovieDBApi {

    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { movie -> movie.actors.any { it.name == actor.name } }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies
            .sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies
            .filter { it.actors.contains(actor) }
            .maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { it.genres.contains(genre) }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies
            .sortedByDescending { it.rating }
            .take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        val eachCount = movies
            .groupingBy { it.director }
            .eachCount()

        return eachCount
            .maxByOrNull { it.value }
            ?.key ?: throw NoSuchElementException("Empty")
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val actorToMovies: Map<MovieActor, Set<Movie>> = movies
            .flatMap { movie -> movie.actors.map { actor -> actor to movie } }
            .groupBy({it.first},{it.second})
            .mapValues { it.value.toSet() } // will need intersect later

        val actors = actorToMovies.keys.toList()

        val pairs = actors.indices.flatMap { i ->
            (i + 1..<actors.size).map { j ->
                    val a = actors[i]
                    val b = actors[j]
                    val common = (actorToMovies[a]!! intersect actorToMovies[b]!!).size
                    Triple(a, b, common)
            }
        }

        val max = pairs.maxOf { it.third }

        return pairs
            .filter { it.third == max }
            .map { (a, b, _) ->
                    // sorting inside pair
                    val sorted = listOf(a, b).sortedBy { it.name }
                    sorted[0] to sorted[1]
            }
            // sorting between the pairs
            .sortedWith(compareBy({it.first.name}, {it.second.name}))
    }

}