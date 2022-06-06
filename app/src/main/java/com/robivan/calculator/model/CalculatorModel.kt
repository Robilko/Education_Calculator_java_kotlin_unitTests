package com.robivan.calculator.model

import java.io.Serializable
import java.lang.StringBuilder

class CalculatorModel : Serializable {
    var firstArg = 0.0
    var secondArg = 0.0
    val argument = StringBuilder()
    val expression = StringBuilder()
    var actionSelected = 0
    var state: State = State.FirstArgInput

    fun getResultValue() = if (argument.isNotEmpty()) argument.toString() else DEFAULT_MEANING
    fun getExpressionValue() = if (expression.isNotEmpty()) expression.toString() else DEFAULT_MEANING

    companion object {
        const val DEFAULT_MEANING = "0"
    }
}