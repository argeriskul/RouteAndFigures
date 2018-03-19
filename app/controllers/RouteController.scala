package controllers

import javax.inject.{Inject, Singleton}

import models.{Figure, Route}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.FigureService

@Singleton
class RouteController @Inject() (cc:ControllerComponents, figureService:FigureService) extends AbstractController(cc) {

  def add: Action[Route] = Action(parse.json[Route]) { request =>
    play.Logger.debug("adding route: {}. Body={}",request, request.body)
    val route:Route = request.body

    Created("Added route:"+route)
  }

  def checkRoute: Action[Route] = Action(parse.json[Route]) { request =>
    val route =request.body

    val intersectedFigures:Seq[Figure] = figureService.calculateIntersections(route)
    Ok("Intersected "+intersectedFigures.size +" figures:"+intersectedFigures)
  }
}
