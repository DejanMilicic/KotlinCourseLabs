package lab8

import common.FileReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private fun parseMovies(moviesLines: List<String>): List<Movie> {
    val header = moviesLines.first().split(";")
    val columnIndex = header.withIndex().associate { (index, name) -> name to index }

    return moviesLines.drop(1)
        .filter { it.isNotBlank() }
        .map { line ->
            val columns = line.split(";")

            val genres = columns[columnIndex.getValue("genres")]
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val actors = columns[columnIndex.getValue("cast")]
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .map { MovieActor(it) }

            Movie(
                title = columns[columnIndex.getValue("title")],
                budget = columns[columnIndex.getValue("budget")].toLong(),
                revenue = columns[columnIndex.getValue("revenue")].toLong(),
                releaseDate = LocalDate.parse(columns[columnIndex.getValue("release_date")], DateTimeFormatter.ISO_LOCAL_DATE),
                runtimeInMinutes = columns[columnIndex.getValue("runtime")].toDouble().toInt(),
                director = MovieDirector(columns[columnIndex.getValue("director")]),
                genres = genres,
                actors = actors,
                rating = columns[columnIndex.getValue("rating")].toDouble()
            )
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}