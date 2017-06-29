package com.github.woooking.elective.dao

import com.github.woooking.elective.model.Course
import com.github.woooking.elective.tables.Courses
import com.github.woooking.elective.tables.Courses._
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object CourseDao {
	def create(course: Course): Course = {
		val action = (Courses.courses returning Courses.courses.map(_.id) into ((c, id) => c.copy(id))) += course
		val result = Await.result(db.run(action), 10 second)
		result
	}

	def get(id: Long): Course = {
		val query = Courses.courses.filter(_.id === id)
		val result = Await.result(db.run(query.result), 10 second)
		result.head
	}

	def getCourses = {
		val query = Courses.courses
		val result = Await.result(db.run(query.result), 10 second)
		result.map(x => x: Course)
	}

	def update(course: Course) = {
		val query = Courses.courses.filter(_.id === course.id)
		db.run(query.update(course))
	}

}
