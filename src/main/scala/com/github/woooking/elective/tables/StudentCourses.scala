package com.github.woooking.elective.tables

import com.github.woooking.elective.model.StudentCourse
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

class StudentCourses(tag: Tag) extends Table[(Long, Long, Int, Option[Int])](tag, "student_course") {
	def student = column[Long]("student")

	def course = column[Long]("course")

	def state = column[Int]("state")

	def score = column[Option[Int]]("score")

	def * = (student, course, state, score)

	def studentID = foreignKey("student_id", student, Users.users)(_.id)

	def courseID = foreignKey("course_id", course, Courses.courses)(_.id)

	def pk = primaryKey("pk", (student, course))
}

object StudentCourses {
	val studentCourses = TableQuery[StudentCourses]

	implicit def sc2tuple(sc: StudentCourse) = (sc.student.id, sc.course.id, sc.state.id, sc.score)
}
