package com.github.woooking

package object elective {
	case class FailureMessage(message: String) extends Exception(message)
}
