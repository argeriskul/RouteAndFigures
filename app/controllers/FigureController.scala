package controllers

import javax.inject.{Inject, Singleton}

import models.{Figure, Point}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.FigureService

/**
  * This controller is responsible for queries about Figure: create, get, check
  */
@Singleton
class FigureController  @Inject() (cc:ControllerComponents, service:FigureService) extends AbstractController(cc) {

  def add: Action[Figure] = Action (parse.json[Figure]) { request =>
    var figure:Figure = request.body
    service.addFigure(figure)
    Created(  "Added "+figure)
  }

  def addPoint: Action[Point] = Action (parse.json[Point]) { request =>
    println(request.body)
    val  point: Point = request.body
    Created("Added point "+point)
  }

}
