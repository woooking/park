package com.github.woooking.elective.model

import com.github.woooking.elective.model.SelectState.SelectState

case class StudentCourse(student: User, course: Course, state: SelectState, score: Option[Int])