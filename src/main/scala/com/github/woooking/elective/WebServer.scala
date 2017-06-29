package com.github.woooking.elective

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.github.woooking.elective.dao._
import com.github.woooking.elective.model.Period
import com.github.woooking.elective.tables.{Courses, StudentCourses, TeacherCourses, Users}
import com.github.woooking.elective.util.TwirlSupport._
import slick.jdbc.SQLiteProfile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn

object WebServer {
	implicit val system = ActorSystem("my-system")
	implicit val materializer = ActorMaterializer()
	implicit val executionContext = system.dispatcher

	var period = Period.Stop

	val secret = "secret"

	def createTables(): Unit = {
		val tables = List(Users.users, Courses.courses, StudentCourses.studentCourses, TeacherCourses.teacherCourses)
		val existing = db.run(MTable.getTables)
		val f = existing.flatMap(v => {
			val names = v.map(mt => mt.name.name)
			val createIfNotExist = tables.filter(table =>
				(!names.contains(table.baseTableRow.tableName))).map(_.schema.create)
			db.run(DBIO.sequence(createIfNotExist))
		})
		Await.result(f, Duration.Inf)
	}

	def main(args: Array[String]) {
		createTables()

		val route = pathSingleSlash {
			complete {
				html.main.render()
			}
		}

		val bindingFuture = Http().bindAndHandle(route, "localhost", 8090)

		println(s"Server online at http://localhost:8090/\nPress RETURN to stop...")
		StdIn.readLine()
		bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

		db.close()
	}

}
