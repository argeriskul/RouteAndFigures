package models

import play.api.libs.json.{Format, Json}

case class Point(x: Int, y: Int) {}

object Point {
  implicit val jsonFormat: Format[Point] = Json.format[Point]
}

case class Figure(vertexList: Seq[Point]) {
  def isConvex: Boolean = {

    // TODO calculate only once
    calculateConvex
  }

  def isDifferentDirection(p1: Point, p2:Point, p3: Point, signum: Int): (Boolean,Int) = {
    val currentSignum = calculateCrossProductSign(p1,p2,p3)
    val firstNonZeroSignum = if (signum != 0) signum else currentSignum

    (signum*currentSignum < 0, firstNonZeroSignum)
  }

  def calculateConvex: Boolean = {
    if (vertexList.lengthCompare(3) < 0) {
      return false
    }
    // add 2 points from the beginning to the end to avoid handling special cases
    val closedFigure = vertexList :+ vertexList(0) :+ vertexList(1)

    var signum = 0
    closedFigure.sliding(3).exists(triple => {
      val result = isDifferentDirection(triple(0), triple(1), triple(2), signum)
      signum = result._2
      result._1
    })
    // if signum is zero then all lines are on the single line
    signum != 0
  }

  def calculateCrossProductSign(p1: Point, p2: Point, p3: Point): Int = {
    val dx1 = p2.x - p1.x
    val dy1 = p2.y - p1.y
    val dx2 = p3.x - p2.x
    val dy2 = p3.y - p2.y
    (dx1 * dy2 - dy1 * dx2).signum
  }

}

object Figure {
  // allow to automatically parse from Json
  implicit val jsonFormat: Format[Figure] = Json.format[Figure]
}
