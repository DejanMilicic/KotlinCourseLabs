package lab8

import common.FileReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


internal fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines.drop(1)
        .map {
            val fields = it.split(";")
            Movie(
                budget = fields[0].toLong(),
                genres = fields[1].split(",").map { genre -> genre.trim() },
                title = fields[2],
                rating = fields[3].toDouble(),
                releaseDate = LocalDate.parse(fields[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                revenue = fields[5].toLong(),
                runtimeInMinutes = fields[6].toDouble().toInt(),
                actors = fields[7].split(",")
                    .filter { actor -> actor.isNotBlank() }
                    .map { actor -> MovieActor(actor.trim()) },
                director = MovieDirector(fields[8])
            )
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)
    println(movies)

    val movieDBApi: MovieDBApi = MovieDB(movies)
}