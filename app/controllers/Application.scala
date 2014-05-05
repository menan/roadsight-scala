package controllers

import play.api.Play.current
import play.api.PlayException

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import com.mongodb.casbah.Imports._
import play.api.libs.json._

import models._

case class ReportData(status: String, long: String, lat: String)

object Application extends Controller {

	val reportForm = Form(
	  mapping(
	  	"status" -> nonEmptyText,
	  	"long" -> nonEmptyText,
	  	"lat" -> nonEmptyText
		)(ReportData.apply)(ReportData.unapply)
	)


  def index =  Action {
    Redirect(routes.Application.reports)
  }

  def reports = Action {
  	Ok(views.html.index(Report.all(),reportForm))
  }
  
  def reportsJSON = Action {
  	Ok(Json.obj("reports" -> Report.all()))
  }
  
  def newReport = Action { implicit request =>
		reportForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index(Report.all(), errors)),
			userData => {
				Report.create(userData.status,userData.long.toFloat,userData.lat.toFloat)
				Redirect(routes.Application.reports)
			}
		)
	}
  
  def deleteReport(id: String) = Action {
    Report.delete(id)
    Redirect(routes.Application.reports)
  }
}