package models

import com.novus.salat.global._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import java.util.Date
import mongoContext._


case class Report(_id: ObjectId = new ObjectId, at:Date = new Date, status: String, long: Float, lat: Float)


object Report extends ModelCompanion[Report, ObjectId]{
	val clientURI = MongoClientURI(System.getenv("MONGOHQ_URL"))
	val client = MongoClient(clientURI)
	val collection =  client("app24784027")("reports")
	val dao = new SalatDAO[Report, ObjectId](collection = collection) {}

    def all(): List[Report] = dao.find(MongoDBObject.empty).toList

	def create(status: String, long: Float, lat: Float){
		dao.insert(Report(status = status, long = long, lat = lat))
	}

	def delete(id: String) {
		dao.remove(MongoDBObject("_id" -> new ObjectId(id)))
	}
}