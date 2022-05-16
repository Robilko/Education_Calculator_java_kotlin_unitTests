package com.robivan.calculator

import java.io.Serializable
import java.lang.NullPointerException
import java.lang.StringBuilder

class CalculatorModel : Serializable {
    private var firstArg = 0.0
    private var secondArg = 0.0
    private val argument = StringBuilder()
    private val expression = StringBuilder()
    private var actionSelected = 0

    private enum class State {
        FirstArgInput, SecondArgInput, ResultShow
    }

    private var state: State

    fun onNumPressed(buttonId: Int) {
        if (state == State.ResultShow) {
            expression.setLength(0)
            argument.setLength(0)
            state = State.FirstArgInput
        }

        if (argument.length < ARG_MAX_LENGTH) {
            if (argument.length == 1 && argument[0] == '0') {
                argument.setLength(0)
            }
            if (buttonId == Util.DOUBLE_ZERO) {
                if (argument.isEmpty()) {
                    argument.append("0")
                } else {
                    argument.append("00")
                }
            } else if (buttonId == Util.DOT) {
                if (argument.isEmpty()) {
                    argument.append("0.")
                } else if (!argument.toString().contains(".")) {
                    argument.append(".")
                }
            } else {
                for (i in Util.NUMBER_IDS.indices) {
                    if (buttonId == Util.NUMBER_IDS[i]) {
                        argument.append(i)
                    }
                }
            }
        }
    }

    fun onActionPressed(actionId: Int) {
        val tmp: Double
        if (argument.isEmpty()) {
            argument.append("0")
        }
        when (actionId) {
            Util.CLEAR -> {
                argument.setLength(0)
                expression.setLength(0)
                state = State.ResultShow
                argument.append("0")
            }
            Util.PLUS_MINUS -> {
                tmp = argument.toString().toDouble() * -1
                argument.setLength(0)
                argument.append(doublePrime(tmp))
            }
            Util.BACKSPACE -> {
                argument.deleteCharAt(argument.length - 1)
                if (argument.isEmpty()) {
                    argument.append("0")
                }
            }
            Util.PERCENT -> {
                if (argument.isEmpty()) {
                    argument.append("0")
                }
                tmp = argument.toString().toDouble() / 100
                argument.setLength(0)
                argument.append(doublePrime(tmp))
            }
            Util.EQUALS -> {
                if (state == State.FirstArgInput) {
                    firstArg = argument.toString().toDouble()
                    state = State.SecondArgInput
                    actionSelected = Util.EQUALS
                }
                if (state == State.SecondArgInput) {
                    secondArg = argument.toString().toDouble()
                    state = State.ResultShow
                    expression.append(argument)
                    expression.append("=")
                    argument.setLength(0)
                    when (actionSelected) {
                        Util.PLUS -> argument.append(doublePrime(firstArg + secondArg))
                        Util.MINUS -> argument.append(doublePrime(firstArg - secondArg))
                        Util.MULTIPLY -> argument.append(doublePrime(firstArg * secondArg))
                        Util.DIVISION -> if (secondArg == 0.0) {
                            throw NullPointerException()
                        } else {
                            argument.append(doublePrime(firstArg / secondArg))
                        }
                        Util.EQUALS -> argument.append(doublePrime(firstArg))
                    }
                }
                if (actionId == Util.PLUS || actionId == Util.MINUS || actionId == Util.DIVISION || actionId == Util.MULTIPLY) {
                    when (state) {
                        State.FirstArgInput -> {
                            firstArg = argument.toString().toDouble()
                            state = State.SecondArgInput
                            expression.append(argument)
                            argument.setLength(0)
                            argument.append("0")
                        }
                        State.SecondArgInput -> {
                            expression.deleteCharAt(expression.length - 1)
                        }
                        State.ResultShow -> {
                            firstArg = argument.toString().toDouble()
                            state = State.SecondArgInput
                            expression.setLength(0)
                            expression.append(argument)
                            argument.setLength(0)
                        }
                    }
                    when (actionId) {
                        Util.PLUS -> {
                            actionSelected = Util.PLUS
                            expression.append("+")
                        }
                        Util.MINUS -> {
                            actionSelected = Util.MINUS
                            expression.append("-")
                        }
                        Util.MULTIPLY -> {
                            actionSelected = Util.MULTIPLY
                            expression.append("×")
                        }
                        Util.DIVISION -> {
                            actionSelected = Util.DIVISION
                            expression.append("÷")
                        }
                    }
                }
            }
            else -> if (actionId == Util.PLUS || actionId == Util.MINUS || actionId == Util.DIVISION || actionId == Util.MULTIPLY) {
                when (state) {
                    State.FirstArgInput -> {
                        firstArg = argument.toString().toDouble()
                        state = State.SecondArgInput
                        expression.append(argument)
                        argument.setLength(0)
                        argument.append("0")
                    }
                    State.SecondArgInput -> {
                        expression.deleteCharAt(expression.length - 1)
                    }
                    State.ResultShow -> {
                        firstArg = argument.toString().toDouble()
                        state = State.SecondArgInput
                        expression.setLength(0)
                        expression.append(argument)
                        argument.setLength(0)
                    }
                }
                when (actionId) {
                    Util.PLUS -> {
                        actionSelected = Util.PLUS
                        expression.append("+")
                    }
                    Util.MINUS -> {
                        actionSelected = Util.MINUS
                        expression.append("-")
                    }
                    Util.MULTIPLY -> {
                        actionSelected = Util.MULTIPLY
                        expression.append("×")
                    }
                    Util.DIVISION -> {
                        actionSelected = Util.DIVISION
                        expression.append("÷")
                    }
                }
            }
        }
    }

    val resultValue: String
        get() = argument.toString()
    val expressionValue: String
        get() = expression.toString()

    private fun doublePrime(number: Double): String {
        var result = ""
        if (number % 1 == 0.0) {
            result += number.toLong()
        } else {
            result += number
        }
        return result
    }

    init {
        state = State.FirstArgInput
    }
    companion object {
        const val ARG_MAX_LENGTH = 15
    }
}