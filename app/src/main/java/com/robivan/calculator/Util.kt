package com.robivan.calculator

object Util {
    const val DOUBLE_ZERO = R.id.double_zero
    const val DOT = R.id.dot

    val NUMBER_IDS = intArrayOf(
        R.id.zero,
        R.id.one,
        R.id.two,
        R.id.three,
        R.id.four,
        R.id.five,
        R.id.six,
        R.id.seven,
        R.id.eight,
        R.id.nine,
        DOUBLE_ZERO,
        DOT
    )
    const val PLUS = R.id.plus
    const val MINUS = R.id.minus
    const val PLUS_MINUS = R.id.plus_minus
    const val MULTIPLY = R.id.multiply
    const val DIVISION = R.id.division
    const val PERCENT = R.id.percent
    const val EQUALS = R.id.equals
    const val CLEAR = R.id.clear
    const val BACKSPACE = R.id.backspace

    val ACTION_IDS = intArrayOf(
        PLUS,
        MINUS,
        MULTIPLY,
        DIVISION,
        PLUS_MINUS,
        EQUALS,
        BACKSPACE,
        PERCENT,
        CLEAR
    )

    const val DEFAULT_MEANING = "0"
}