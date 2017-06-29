package com.github.woooking.elective.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.woooking.elective.model.CourseType.CourseType
import com.github.woooking.elective.model.Department.Department
import com.github.woooking.elective.model.UserType.UserType
import com.github.woooking.elective.model._
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
	implicit object DepartmentFormat extends JsonFormat[Department] {
		override def read(json: JsValue): Department = json match {
			case JsString(s) => Department.withName(s)
			case _ => deserializationError("")
		}

		override def write(obj: Department): JsValue = JsString(obj.toString)
	}

	implicit object UserTypeFormat extends JsonFormat[UserType] {
		override def read(json: JsValue): UserType = json match {
			case JsNumber(id) => UserType(id.toInt)
			case _ => deserializationError("")
		}

		override def write(obj: UserType): JsValue = JsNumber(obj.id)
	}

	implicit object CourseTypeFormat extends JsonFormat[CourseType] {
		override def read(json: JsValue): CourseType = json match {
			case JsString(s) => CourseType.withName(s)
			case _ => deserializationError("")
		}

		override def write(obj: CourseType): JsValue = JsString(obj.toString)
	}

	implicit val userFormat = jsonFormat4(User)

	implicit val courseFormat = jsonFormat11(Course)

}

