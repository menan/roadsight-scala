package models

import com.novus.salat.global._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import java.util.Date
import play.api.libs.json._
import mongoContext._


case class Report(_id: ObjectId = new ObjectId, at:Date = new Date, status: String, address:String, long: Float, lat: Float)


object Report extends ModelCompanion[Report, ObjectId]{

	val clientURI = MongoClientURI(System.getenv("MONGOHQ_URL"))
	val client = MongoClient(clientURI)
	val collection =  client("app24891995")("reports")


	val dao = new SalatDAO[Report, ObjectId](collection = collection) {}


  implicit val objectIdWrites = new Writes[ObjectId] {
      def writes(oId: ObjectId): JsValue = {
        JsString(oId.toString)
      }
  }

	implicit val implicitReportWrites = Json.writes[Report]



    def all(): List[Report] = dao.find(MongoDBObject.empty).toList

	def create(status: String, address:String, long: Float, lat: Float){
		dao.insert(Report(status = status, address = address, long = long, lat = lat))
	}

	def delete(id: String) {
		dao.remove(MongoDBObject("_id" -> new ObjectId(id)))
	}
}