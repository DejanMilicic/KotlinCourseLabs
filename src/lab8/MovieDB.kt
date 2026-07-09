package lab8

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

class MovieDB(private val movies: List<Movie>): MovieDBApi {

    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies
            .filter { movie -> movie.actors.contains(actor) }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies
            .sortedByDescending { it.revenue - it.budget }
            .take(numOfMovies)
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies
            .filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies
            .filter { movie -> movie.genres.any { it.equals(genre, ignoreCase = true) } }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies
            .sortedByDescending { it.rating }
            .take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies
            .groupBy { it.director }
            .maxByOrNull { it.value.size }!!.key
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies
            .filter { actor in it.actors }
            .maxByOrNull { it.rating }
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        for(movie in movies) {
            val actors = movie.actors

            for(i in actors.indices) {
                for(j in i + 1 until actors.size) {
                    val pair = if(actors[i].name < actors[j].name)
                        Pair(actors[i], actors[j])
                    else
                        Pair(actors[j], actors[i])

                    pairCounts[pair] = pairCounts.getOrDefault(pair, 0) + 1
                }
            }
        }

        val maxCount = pairCounts.values.maxOrNull() ?: 0

        return pairCounts
            .filter { it.value == maxCount}
            .map { it.key }
    }
}