package models

import org.specs2.mutable.Specification

class FigureSpec extends Specification {

  "Figure should check it convex when" should {
    "it's just a line" in {
      Figure(Seq(Point(0,0), Point(1,1))).isConvex must beFalse
    }
    "its' triangle" in {
      Figure(Seq(Point(0,0), Point(1,2), Point(2,1))).isConvex must beTrue
    }

    "have some vertexes on the line" in {
      Figure(Seq(Point(0,0), Point(0,1), Point(0,3),Point(1,1),Point(1,0))).isConvex must(beTrue)
    }

    "have ALL vertexes on the line" in {
      Figure(Seq(Point(0,0), Point(0,1), Point(0,2),Point(0,3))).isConvex must(beFalse)
    }

    "it's three points on the line" in {
      Figure(Seq(Point(0,0), Point(0,1), Point(0,2))).isConvex must(beFalse)
    }

    "like a pentagram" in {
      Figure(Seq(Point(0,0), Point(3,5), Point(0,5), Point(5,0), Point(5,5))).isConvex must beFalse
    }

    "it has many vertex" in {
      // TODO some generator?
      Figure(Seq(Point(0,0), Point(1,1), Point(2,4), Point(3,9))).isConvex must beTrue
    }

    "not a convex" in {
      Figure(Seq(Point(0,0), Point(1,1), Point(2,0), Point(3,3), Point(0,4))).isConvex must beFalse
    }
  }

  "Figure convex check " should {
    "calculate convex only once" in {
      // TODO implement
      true must(beFalse)
    }
  }

}
