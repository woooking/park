package com.github.woooking.elective.model

import com.github.woooking.elective.dao.StudentCourseDao
import com.github.woooking.elective.model.CourseType.CourseType
import com.github.woooking.elective.model.Department.Department

case class Course(id: Long, name: String, teacher: String, date: Int, timeStart: Int, timeEnd: Int,
                  kind: CourseType, department: Department, description: String, limit: Int, credit: Int
                 ) {
    def isFull() = {
        val studentsNum = StudentCourseDao.getSelectedStudentsNum(id)
        studentsNum >= limit
    }
}
