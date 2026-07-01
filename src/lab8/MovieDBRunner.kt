package lab8

import common.FileReader
import lab5.Direction
import java.time.LocalDate


 fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines
            .drop(1)
            .map { line->
                val parts=line.split(";")
                Movie(
                        budget = parts[0].trim().toLong(),
                        genres = parts[1].trim().split(","),
                        title=parts[2].trim(),
                        rating = parts[3].trim().toDouble(),
                        releaseDate = LocalDate.parse(parts[4].trim()),
                        revenue = parts[5].trim().toLong(),
                        runtimeInMinutes = parts[6].trim().toDouble().toInt(),
                        actors = parts[7].trim().split(",")
                                .filter { it.isNotEmpty() }
                                .map { MovieActor(it.trim()) },
                        director = MovieDirector(parts[8].trim())
                )
            }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}