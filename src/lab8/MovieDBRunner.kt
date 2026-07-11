package lab8

import common.FileReader
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    //TODO("Implement parsing of the file content")
    val moviesLinesHeaderRemoved = moviesLines.drop(1)
    val movies : MutableList<Movie> = mutableListOf()
    moviesLinesHeaderRemoved.forEach { line ->
        val data = line.split(";")
        val movieActorsClass = data[7]
            .split(",")
            .map { actorName -> actorName.trim() }
            .filter { actorName -> actorName.isNotBlank() }
            .map { actorName -> MovieActor(actorName) }
        movies.add(Movie(
            data[2].trim(),
            data[0].trim().toLong(),
            data[5].trim().toLong(),
            LocalDate.parse(data[4].trim()),
            data[6].trim().split(".")[0].trim().toInt(),
            MovieDirector(data[8].trim()),
            data[1].split(",").map { genre -> genre.trim() },
            movieActorsClass,
            data[3].trim().toDouble(),
        ))
    }
    return movies
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("resources/lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)


}