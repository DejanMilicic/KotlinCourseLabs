package lab8

import common.FileReader
import java.time.LocalDate


internal fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines
        .drop(1)
        .mapNotNull { line ->
            runCatching {
                val parts = line.split(";")
                Movie(
                    title = parts[2].trim(),
                    budget = parts[0].trim().toLong(),
                    revenue = parts[5].trim().toLong(),
                    releaseDate = LocalDate.parse(parts[4].trim()),
                    runtimeInMinutes = parts[6].trim().toDouble().toInt(),
                    director = MovieDirector(parts[8].trim()),
                    genres = parts[1].trim().split(",").map { it.trim() },
                    actors = parts[7].trim().split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .map { MovieActor(it) },
                    rating = parts[3].trim().toDouble()
                )
            }.getOrNull()
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)
    val movieDBApi: MovieDBApi = MovieDB(movies)

    println("Best rated movies: ${movieDBApi.getBestRatedMovies(5).map { it.title }}")
    println("Director with most movies: ${movieDBApi.getDirectorWithMostMoviesDirected().name}")
}