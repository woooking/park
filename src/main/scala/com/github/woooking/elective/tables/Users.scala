package com.github.woooking.elective.tables

import com.github.woooking.elective.model.{Department, User, UserType}
import slick.lifted.Tag
import slick.jdbc.SQLiteProfile.api._

class Users(tag: Tag) extends Table[(Long, String, Int, Int)](tag, "user") {
	def id = column[Long]("id", O.PrimaryKey)

	def name = column[String]("name")

	def department = column[Int]("department")

	def t = column[Int]("type")

	def * = (id, name, department, t)
}

object Users {
	val users = TableQuery[Users]

	implicit def tuple2user(t: (Long, String, Int, Int)) = User(t._1, t._2, Department(t._3), UserType(t._4))
	implicit def user2tuple(u: User) = (u.id, u.name, u.department.id, u.t.id)
}