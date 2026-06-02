package lab8

import common.FileReader
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines.drop(1)
        .filter { it.isNotBlank() }
        .map { line ->
            val parts = line.split(";")

            Movie(
                title = parts[2],
                budget = parts[0].toLong(),
                revenue = parts[5].toLong(),
                releaseDate = LocalDate.parse(parts[4]),
                runtimeInMinutes = parts[6].toDouble().toInt(),
                director = MovieDirector(parts[8]),
                genres = parts[1].split(",")
                    .map { it.trim().replace("_", " ") },
                actors = parts[7].split(",")
                    .filter { it.isNotBlank() }
                    .map { MovieActor(it.trim()) },
                rating = parts[3].toDouble()
            )
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)

}