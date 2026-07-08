package lab8

import common.FileReader
import java.time.LocalDate

internal fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines
        .drop(1)
        .filter { it.isNotBlank() }
        .map { line ->
            val parts = line.split(";")
            val budget = parts[0].trim().toLong()
            val genres = parts[1].split(",").map { it.trim() }.filter { it.isNotBlank() }
            val title = parts[2].trim()
            val rating = parts[3].trim().toDouble()
            val releaseDate = LocalDate.parse(parts[4].trim())
            val revenue = parts[5].trim().toLong()
            val runtimeInMinutes = parts[6].trim().toDouble().toInt()
            val actors = parts[7].split(",").map { it.trim() }.filter { it.isNotBlank() }.map(::MovieActor)
            val director = MovieDirector(parts[8].trim())

            Movie(
                title = title,
                budget = budget,
                revenue = revenue,
                releaseDate = releaseDate,
                runtimeInMinutes = runtimeInMinutes,
                director = director,
                genres = genres,
                actors = actors,
                rating = rating
            )
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi: MovieDBApi = MovieDB(movies)
}