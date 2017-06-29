package com.github.woooking.elective.model

object Period extends Enumeration {
	type Period = Value

	val Stop = Value("系统关闭")
	val PreSelect = Value("预选阶段")
	val Draw = Value("抽签中")
	val Select = Value("补退选阶段")

}
