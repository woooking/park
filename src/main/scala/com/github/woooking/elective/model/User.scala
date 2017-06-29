package com.github.woooking.elective.model

import com.github.woooking.elective.model.Department.Department
import com.github.woooking.elective.model.UserType.UserType

case class User(id: Long, name: String, department: Department, t: UserType)
