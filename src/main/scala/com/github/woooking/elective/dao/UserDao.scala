package com.github.woooking.elective.dao

import com.github.woooking.elective.model.User
import com.github.woooking.elective.tables.Users
import com.github.woooking.elective.tables.Users._
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object UserDao {
	def create(user: User) = {
		val action = DBIO.seq(Users.users += user)
		db.run(action)
	}

	def getStudent(id: Long): User = {
		val query = Users.users.filter(_.id === id).filter(_.t === 0)
		val result = Await.result(db.run(query.result), 10 second)
		result.head
	}

	def getUser(id: Long): User = {
		val query = Users.users.filter(_.id === id)
		val result = Await.result(db.run(query.result), 10 second)
		result.head
	}

	def getUsers = {
		val query = Users.users
		val result = Await.result(db.run(query.result), 10 second)
		result.map(x => x: User)
	}

	def update(user: User) = {
		val query = Users.users.filter(_.id === user.id).filter(_.t === 0)
		db.run(query.update(user))
	}

}
