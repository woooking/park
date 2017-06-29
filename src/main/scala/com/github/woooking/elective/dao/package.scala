package com.github.woooking.elective

import slick.jdbc.SQLiteProfile.api._

package object dao {
	val db = Database.forURL("jdbc:sqlite:db.sqlite", driver="org.sqlite.JDBC")
}
