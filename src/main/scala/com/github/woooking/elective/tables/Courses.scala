package com.github.woooking.elective.tables

import com.github.woooking.elective.model.{Course, CourseType, Department}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

class Courses(tag: Tag) extends Table[(Long, String, String, Int, Int, Int, Int, Int, String, Int, Int)](tag, "courses") {
	def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
	def name = column[String]("name")
	def teacher = column[String]("teacher")
	def date = column[Int]("date")
	def timeStart = column[Int]("time_start")
	def timeEnd = column[Int]("time_end")
	def kind = column[Int]("kind")
	def department = column[Int]("department")
	def description = column[String]("description")
	def limit = column[Int]("limit")
	def credit = column[Int]("credit")

	def * = (id, name, teacher, date, timeStart, timeEnd, kind, department, description, limit, credit)
}

object Courses {
	val courses = TableQuery[Courses]

	implicit def tuple2course(t: (Long, String, String, Int, Int, Int, Int, Int, String, Int, Int)) =
		Course(t._1, t._2, t._3, t._4, t._5, t._6, CourseType(t._7), Department(t._8), t._9, t._10, t._11)
	implicit def course2tuple(u: Course) =
		(u.id, u.name, u.teacher, u.date, u.timeStart, u.timeEnd, u.kind.id, u.department.id, u.description, u.limit, u.credit)
}
