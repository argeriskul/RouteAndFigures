package services

import javax.inject.Singleton

import models.{Figure, Point, Route}

import scala.collection.mutable.ArrayBuffer

@Singleton
class FigureService {

  val figures: ArrayBuffer[Figure] = collection.mutable.ArrayBuffer[Figure]()

  def addFigure(figure: Figure): Unit = {
    figures += figure
    play.Logger.debug("after adding:" + figures)
  }
  def calculateIntersections(route: Route): Seq[Figure] = {
    val result = new ArrayBuffer[Figure](figures.size)
    for (figure <- figures) {
      if (isIntersect(route, figure)) {
        result += figure
      }
    }
    result
  }

  def isIntersect(route: Route, figure: Figure): Boolean = {
    route.checkpoints.sliding(2).exists(pair => isIntersect(pair(0), pair(1), figure))
  }


  def isIntersect(p1: Point, p2: Point, figure: Figure): Boolean = {
    figure.vertexList.sliding(2).exists(pair => isSegmentsIntersect(p1,p2,pair(0), pair(1)))
  }

  // allow access from the current package because there are tests for this method
  private[services] def isSegmentsIntersect(p1: Point, p2: Point, b1: Point, b2: Point): Boolean = {
    val r = Point(p2.x - p1.x, p2.y - p1.y)
    val s = Point(b2.x - b1.x, b2.y - b1.y)
    val rCrossS = crossProduct(r, s)
    val qMinusP = Point(b1.x - p1.x, b1.y - p1.y)
    if (rCrossS == 0) {
      // segments are parallel or overlap
      val rSquared = scalarProduct(r, r).floatValue()
      // express vector s in terms of t - parametric view of r
      // the begining of s
      val t0 = scalarProduct(qMinusP, r) / rSquared
      // the end of s
      val t1 = scalarProduct(s, r) / rSquared + t0
      // r described as (0,1)*t. Check that s falls in this interval
      (0 <= t0 && t0 <= 1) || (0 <= t1 && t1 <= 1)
    } else {
      // lines intersect, check segments
      val t = crossProduct(qMinusP, s) / rCrossS.floatValue()
      val u = crossProduct(qMinusP, r) / rCrossS.toFloat
      play.Logger.debug(s"t=$t, u=$u")
      // segments intersect only both t and u are in range [0..1]
      0 <= t && t <= 1 && 0 <= u && u <= 1
    }
  }

  private def crossProduct(r: Point, s: Point): Int = {
    r.x * s.y - r.y * s.x
  }

  private def scalarProduct(a: Point, b: Point): Int = {
    a.x * b.x + a.y * b.y
  }
}
