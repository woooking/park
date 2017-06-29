package com.github.woooking.elective.tables

import com.github.woooking.elective.dao.UserDao
import com.github.woooking.elective.model.TeacherCourse
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

class TeacherCourses(tag: Tag) extends Table[(Long, Long)](tag, "teacher_course") {
	def teacher = column[Long]("teacher")

	def course = column[Long]("course")

	def * = (teacher, course)

	def teacherID = foreignKey("teacher_id", teacher, Users.users)(_.id)

	def courseID = foreignKey("course_id", course, Courses.courses)(_.id)
}

object TeacherCourses {
	val teacherCourses = TableQuery[TeacherCourses]

	implicit def tc2tuple(tc: TeacherCourse) = (tc.teacher.id, tc.course.id)
}