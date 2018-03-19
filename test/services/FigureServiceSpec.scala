package services

import models.{Figure, Point, Route}
import org.specs2.mutable.Specification


class FigureServiceSpec extends Specification {
  isolated

  val service = new FigureService()

  val squareWithSide4 = Figure(Seq(Point(0, 0), Point(4, 0), Point(4, 4), Point(0, 4)))

  "FigureService" should {
    "add figures" in {
      service.addFigure(squareWithSide4)
      service.figures.size mustEqual 1
    }

    "check segments intersection" in {
      "parallel" in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1), Point(10,10), Point(11,11)) must beFalse
      }
      "on the same line but not overlap" in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1),Point(2,2), Point(3,3)) must beFalse
      }

      "lines intersect but segments are too far from each other" in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1),Point(0,10), Point(10,5)) must beFalse
      }

      "intersect somewhere in middle" in {
        service.isSegmentsIntersect(Point(0,0), Point(10,10),Point(0,10), Point(10,0)) must beTrue
      }
      "on the same line and overlap" in {
        service.isSegmentsIntersect(Point(0,0), Point(2,2),Point(1,1), Point(3,3)) must beTrue
      }
      "on the same line and have only 1 common point" in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1),Point(1,1), Point(3,3)) must beTrue
      }
      "intersect and have only 1 common point" in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1),Point(1,1), Point(2,3)) must beTrue
      }
      "lines intersect but segments are not " in {
        service.isSegmentsIntersect(Point(0,0), Point(1,1),Point(0,10), Point(10,0)) must beFalse
      }
    }

    "check polygon and segment intersection" in {
      "too far" in {
        service.isIntersect(Point(10,10), Point(20,20), squareWithSide4) must beFalse
      }

      "segment inside" in {
        service.isIntersect(Point(1,1), Point(2,2), Figure(Seq(Point(0,0), Point(100, 0), Point(0,100)))) must beFalse
      }
      "end in Figure" in {
        service.isIntersect(Point(10,10), Point(1,1), squareWithSide4) must beTrue
      }

      "intersect edge between last and first vertexes" in {
        service.isIntersect(Point(-1,0), Point(1,5), squareWithSide4) must beTrue
      }

    }

    "return polygons which intersected by path" in {
      service.addFigure(squareWithSide4)
      service.addFigure(Figure(Seq(Point(0,5), Point(5,5), Point(5,0))))
      val farAway = Figure(Seq(Point(100, 50), Point(50, 50), Point(50, 100)))
      service.addFigure(farAway)
      val actual = service.calculateIntersections(Route(Seq(Point(-5,-5),Point(0,0), Point(0,10))))
      actual.size mustEqual 2
      actual.contains(squareWithSide4) must beTrue
      actual.contains(farAway) must beFalse
    }
  }


}
