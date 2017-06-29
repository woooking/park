package com.github.woooking.elective.dao

import com.github.woooking.elective.model.SelectState.SelectState
import com.github.woooking.elective.model.{Course, SelectState, StudentCourse, User}
import com.github.woooking.elective.tables.Courses._
import com.github.woooking.elective.tables.Users._
import com.github.woooking.elective.tables.{Courses, StudentCourses, Users}
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object StudentCourseDao {
	def create(studentID: Long, courseID: Long, state: SelectState) = {
		val action = StudentCourses.studentCourses += (studentID, courseID, state.id, None)
		db.run(action)
	}

	def createOrUpdate(studentID: Long, courseID: Long, state: SelectState) = {
		val action = StudentCourses.studentCourses.insertOrUpdate((studentID, courseID, state.id, None))
		db.run(action)
	}

	def delete(studentID: Long, courseID: Long) = {
		val action = StudentCourses.studentCourses.filter(_.student === studentID).filter(_.course === courseID).delete
		db.run(action)
	}

	def update(studentID: Long, courseID: Long, state: SelectState) = {
		val action = StudentCourses.studentCourses.filter(_.student === studentID).filter(_.course === courseID).map(_.state).update(state.id)
		db.run(action)
	}

	def updateScore(studentID: Long, courseID: Long, score: Int) = {
		val action = StudentCourses.studentCourses.filter(_.student === studentID).filter(_.course === courseID).map(_.score).update(Some(score))
		db.run(action)
	}

	def updateEnd() = {
		val action = StudentCourses.studentCourses.filter(_.state === SelectState.Selected.id).map(_.state).update(SelectState.End.id)
		db.run(action)
	}

	def getPreSelectStudents(courseID: Long) = {
		val query = StudentCourses.studentCourses.filter(_.course === courseID).filter(_.state === SelectState.PreSelect.id)
		val result = Await.result(db.run(query.result), 10 second)
		result
	}

	def getSelectedStudents(courseID: Long) = {
		val query = (Users.users join StudentCourses.studentCourses.filter(_.course === courseID).filter(_.state === SelectState.Selected.id) on (_.id === _
			.student)).map(_._1)
		val result = Await.result(db.run(query.result), 10 second)
		result.map(x => x: User)
	}

	def getSelectedByCourse(courseID: Long) = {
		val query = StudentCourses.studentCourses.filter(_.course === courseID).filter(_.state === SelectState.Selected.id)
		val result = Await.result(db.run(query.result), 10 second)
		result
	}

	def getEndByStudent(studentID: Long) = {
		val query = StudentCourses.studentCourses.filter(_.student === studentID).filter(_.state === SelectState.End.id)
		val result = Await.result(db.run(query.result), 10 second)
		result
	}

	def getEndByCourse(courseID: Long) = {
		val query = StudentCourses.studentCourses.filter(_.course === courseID).filter(_.state === SelectState.End.id)
		val result = Await.result(db.run(query.result), 10 second)
		result
	}

	def getSelectedStudentsNum(courseID: Long) = {
		val query = StudentCourses.studentCourses.filter(_.course === courseID).filter(_.state === SelectState.Selected.id).length
		val result = Await.result(db.run(query.result), 10 second)
		result
	}

	def getSelectableCourses(studentID: Long) = {
		val query = for {
			(c, sc) <- Courses.courses joinLeft StudentCourses.studentCourses.filter(_.student === studentID) on (_.id === _.course)
		} yield (c, sc.map(_.state))

		val result = Await.result(db.run(query.result), 10 second)
		result.filter {
			case (_, Some(s)) =>
				s != SelectState.PreSelect.id && s != SelectState.Selected.id && s != SelectState.Prohibit.id && s != SelectState.End.id
			case _ => true
		}.map {
			case (c, _) => c: Course
		}
	}

	def getSelectedCourses(studentID: Long) = {
		val query = for {
			(c, sc) <- Courses.courses join StudentCourses.studentCourses on (_.id === _.course)
			if ((sc.state === SelectState.PreSelect.id) || (sc.state === SelectState.Selected.id) || (sc.state === SelectState.WithDraw.id) || (sc
				.state === SelectState.UnSelected.id)) && (sc.student === studentID)
		} yield (c, sc.state)

		val result = Await.result(db.run(query.result), 5 second)
		result.map {
			case (c, s) => (c: Course, SelectState(s))
		}
	}

	def getPassedCourses(studentID: Long) = {
		val query = for {
			(c, sc) <- Courses.courses join StudentCourses.studentCourses.filter(_.student === studentID) on (_.id === _.course)
			if sc.state === SelectState.End.id
		} yield c

		val result = Await.result(db.run(query.result), 10 second)
		result.map(c => c: Course)
	}
}
