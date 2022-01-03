package streams

import scala.util.Sorting

object Main extends App {
  case class Movie(title: String, rating: Int, duration: Int)

  val movies = Array(
    Movie("Interstellar", 9, 169),
    Movie("Inglourious Basterds", 8, 140),
    Movie("Fight Club", 9, 139),
    Movie("Zodiac", 8, 157)
  )

  implicit val orderMovieByTitle: Ordering[Movie] = {
    new Ordering[Movie] {
      def compare(a: Movie,b: Movie) = a.title compare b.title
    }
  }
  implicit val orderMovieByRating: Ordering[Movie] = {
    new Ordering[Movie] {
      def compare(a: Movie, b: Movie) = a.rating compare b.rating
    }
  }
  implicit val orderMovieByDuration: Ordering[Movie] = {
    new Ordering[Movie] {
      def compare(a: Movie, b: Movie) = a.duration compare b.duration
    }
  }

  implicit def orderingPair[A, B](implicit
                                  orderingA: Ordering[A],
                                  orderingB: Ordering[B]
                                 ): Ordering[(A, B)] =
    new Ordering[(A, B)] {
      def compare(pair1: (A, B), pair2: (A, B)): Int = {
        val firstCriteria = orderingA.compare(pair1._1, pair2._1)
        if (firstCriteria != 0) firstCriteria
        else orderingB.compare(pair1._2, pair2._2)
      }
    }


  //Sorting.quickSort(movies)(movie => (movie.rating, movie.title))(orderingPair(Ordering.Int,Ordering.String))
  Sorting.quickSort(movies)(orderMovieByTitle)
  movies.foreach(movie => println(movie.title))
  println("==================================")
  Sorting.quickSort(movies)(orderMovieByRating)
  movies.foreach(movie => println(movie.title))
  println("==================================")
  Sorting.quickSort(movies)(orderMovieByDuration)
  movies.foreach(movie => println(movie.title))

}