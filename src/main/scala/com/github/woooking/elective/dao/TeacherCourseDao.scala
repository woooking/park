package com.github.woooking.elective.dao

import com.github.woooking.elective.model.{Course, TeacherCourse}
import com.github.woooking.elective.tables.Courses._
import com.github.woooking.elective.tables.TeacherCourses._
import com.github.woooking.elective.tables.{Courses, TeacherCourses}
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._


object TeacherCourseDao {
	def create(teacherCourse: TeacherCourse) = {
		val action = DBIO.seq(TeacherCourses.teacherCourses += teacherCourse)
		db.run(action)
	}

	def getCourses(id: Long) = {
		val query = for {
			(tc, c) <- TeacherCourses.teacherCourses.filter(_.teacher === id) join Courses.courses on (_.course === _.id)
		} yield c

		val result = Await.result(db.run(query.result), 10 second)
		result.map(x => x: Course)
	}

}
