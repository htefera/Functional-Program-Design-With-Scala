case class Person(name: String)
case class Paper(title: String, authors: List[Person], body: String)

object ConfManagement:
  type Viewers = Set[Person] //viewers is a type alias for a set of persons



  class Conference(ratings: (Paper, Int)*):
    private val realScore = ratings.toMap
    def papers: List[Paper] = ratings.map(_._1).toList

    def score(paper: Paper, viewers: Viewers): Int =
      if paper.authors.exists(viewers.contains) then -100
      else realScore(paper)
    def rankings(viewers: Viewers): List[Paper] =
      papers.sortBy(score(_, viewers)).reverse
    def ask[T](p: Person, query: Viewers => T) =
      query(Set(p))
    def delegateTo[T](p: Person, query: Viewers => T)(viewers: Viewers): T =
      query(viewers + p)

  end Conference
end ConfManagement

import ConfManagement._

val Smith = Person("Smith")
val Peters = Person("Peters")
val Abel = Person("Abel")
val Black = Person("Black")
val Ed = Person("Ed")

val conf = Conference(
  Paper("How to grow beans", List(Smith, Peters), "...") -> 92,
Paper("Organic gardening", List(Abel, Peters), "...") -> 83,
Paper("Composting done right", List(Black, Smith), "...") -> 99,
Paper("The secret life of snails", authors = List(Ed), "...") -> 77
)



//Which authors have at least two papers with a score over 80?

def highlyRankedProlificAuthors(asking: Person): Set[Person] =
  def query(viewers: Viewers): Set[Person] =
      val highlyRanked = conf.rankings(viewers).takeWhile(conf.score(_, viewers) > 80).toSet
      for
        p1 <- highlyRanked
        p2 <- highlyRanked
        author <- p1.authors
        if p1 != p2 && p2.authors.contains(author)
      yield author
  conf.ask(asking, query) //this is added because the answer depends on who is asking!
def testAs(person:Person)={
  highlyRankedProlificAuthors(asking = person).map(_.name).mkString(",")
}


testAs(Black)
testAs(Abel)
testAs(Smith)
testAs(Ed)

//Nothing prevents just passing the empty set of viewers to a query.
/** When asking a query, we have to pass a Viewers set to the conference
management methods.
But Viewers is an unknown abstract type; hence there is no way to create
a Viewers instance outside the ConfManagement object.
So the only way to get a viewers value is in the parameter of a query,
where the conference management system provides the actual value.
 the following question generates an error*/


conf.rankings(Set()).takeWhile(conf.score(_, Set()) > 80)

/**
 * Back to the conference management code:
 * One downside is that we have to pass viewers arguments along
 * everywhere they are needed.
 * This seems pointless, since by design there is only a single value we could pass!
 * It also quickly gets tedious as the codebase grows.
 * Can’t this be automated?
 * Of course: Just use implicit parameters.
  */










