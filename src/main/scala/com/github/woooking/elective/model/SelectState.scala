package com.github.woooking.elective.model

object SelectState extends Enumeration {
	type SelectState = Value

	val PreSelect = Value("预选")
	val Selected = Value("选上")
	val UnSelected = Value("未选上")
	val WithDraw = Value("退选")
	val Prohibit = Value("禁选")
	val End = Value("已修")
}
