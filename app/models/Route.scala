package models

import play.api.libs.json.{Format, Json}

case class Route (checkpoints:Seq[Point]){

}

object Route {
  implicit val jsonFormat: Format[Route] = Json.format[Route]
}