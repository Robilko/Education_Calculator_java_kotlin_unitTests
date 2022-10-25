package com.robivan.calculator.presenter

import com.robivan.calculator.util.ButtonsIds
import com.robivan.calculator.model.CalculatorModel
import com.robivan.calculator.model.State

class MainPresenter : MainContract.MainPresenter {

    private lateinit var model: CalculatorModel
    private lateinit var view: MainContract.MainView

    override fun onAttach(model: CalculatorModel, view: MainContract.MainView) {
        this.model = model
        this.view = view
        showResult()
        showExpression()
    }

    override fun onDetach(): CalculatorModel = model

    //Метод для обработки нажатий кнопок с цифрами и "."
    override fun onNumPressed(buttonId: Int) {
        resetForNewExpressionIfPreviousIsFinished()

        if (model.argument.length < ARG_MAX_LENGTH) {
            clearZeroEqualsArgument()

            when (buttonId) {
                ButtonsIds.DOUBLE_ZERO -> {
                    onDoubleZeroButtonPressed()
                }
                ButtonsIds.DOT -> {
                    onDotButtonPressed()
                }
                else -> {
                    onNumbersButtonPressed(buttonId)
                }
            }
        }
        showResult()
    }

    //Сброс значений аргументов и вычислений в модели, если предыдущее вычисление окончено
    private fun resetForNewExpressionIfPreviousIsFinished() = with(model) {
        if (state == State.ResultShow) {
            expression.setLength(DEFAULT_VALUE_LENGTH)
            argument.setLength(DEFAULT_VALUE_LENGTH)
            state = State.FirstArgInput
        }
    }

    //Очистка значения от нуля в начале, если это цифра первого порядка в аргументе
    private fun clearZeroEqualsArgument() = with(model) {
        if (argument.length == 1 && argument[0] == '0') {
            argument.setLength(DEFAULT_VALUE_LENGTH)
        }
    }

    //Обработка нажатия кнопки "00"
    private fun onDoubleZeroButtonPressed() = with(model) {
        if (argument.isEmpty()) {
            argument.append(DEFAULT_ZERO_VALUE)
        } else {
            argument.append(DOUBLE_ZERO_VALUE)
        }
    }

    //Обработка нажатия кнопки "."
    private fun onDotButtonPressed() = with(model) {
        if (argument.isEmpty()) {
            argument.append("0.")
        } else if (!argument.toString().contains(".")) {
            argument.append(".")
        } else {
            return@with
        }
    }

    //Обработка нажатия кнопок с цифрами
    private fun onNumbersButtonPressed(buttonId: Int) {
        for (i in ButtonsIds.NUMBER_IDS.indices) {
            if (buttonId == ButtonsIds.NUMBER_IDS[i]) {
                model.argument.append(i)
            }
        }
    }

    //Метод обработки нажатия кнопок математических действий
    override fun onActionPressed(actionId: Int) {
        setDefaultValueToEmptyArgument()

        when (actionId) {
            ButtonsIds.CLEAR -> {
                onClearButtonPressed()
            }
            ButtonsIds.PLUS_MINUS -> {
                onPlusMinusButtonPressed()
            }
            ButtonsIds.BACKSPACE -> {
                onBackspaceButtonPressed()
            }
            ButtonsIds.PERCENT -> {
                onPercentButtonPressed()
            }
            ButtonsIds.EQUALS -> {
                onEqualsButtonPressed(actionId)
            }
            else -> onStandardMathActionPressed(actionId)
        }
        showResult()
        showExpression()
    }

    //Обработка нажатия кнопки очистки
    private fun onClearButtonPressed() = with(model) {
        argument.setLength(DEFAULT_VALUE_LENGTH)
        expression.setLength(DEFAULT_VALUE_LENGTH)
        state = State.ResultShow
        setZeroValueToArgument()
    }

    //Обработка нажатия кнопки "±"
    private fun onPlusMinusButtonPressed() = with(model) {
        val tmp = argumentToDouble() * -1
        argument.setLength(0)
        argument.append(doublePrime(tmp))
    }

    //Обработка нажатия кнопки очистки последнего символа
    private fun onBackspaceButtonPressed() = with(model) {
        argument.deleteCharAt(argument.length - 1)
        setDefaultValueToEmptyArgument()
    }

    //Обработка нажатия кнопки "%"
    private fun onPercentButtonPressed() = with(model) {
        setDefaultValueToEmptyArgument()
        val tmp = argumentToDouble() / 100
        argument.setLength(DEFAULT_VALUE_LENGTH)
        argument.append(doublePrime(tmp))
    }

    //Обработка нажатия кнопки "="
    private fun onEqualsButtonPressed(actionId: Int) = with(model) {
        if (state == State.FirstArgInput) {
            firstArg = argumentToDouble()
            state = State.SecondArgInput
            actionSelected = ButtonsIds.EQUALS
        }
        if (state == State.SecondArgInput) {
            secondArg = argumentToDouble()
            state = State.ResultShow
            expression.append(argument)
            expression.append("=")
            argument.setLength(DEFAULT_VALUE_LENGTH)

            when (actionSelected) {
                ButtonsIds.PLUS -> argument.append(doublePrime(firstArg + secondArg))
                ButtonsIds.MINUS -> argument.append(doublePrime(firstArg - secondArg))
                ButtonsIds.MULTIPLY -> argument.append(doublePrime(firstArg * secondArg))
                ButtonsIds.DIVISION -> if (secondArg == 0.0) {
                    view.showError()
                } else {
                    argument.append(doublePrime(firstArg / secondArg))
                }
                ButtonsIds.EQUALS -> argument.append(doublePrime(firstArg))
            }
        }
        onStandardMathActionPressed(actionId)
    }

    //Обработка нажатия кнопок: "+", "-", "×", "÷"
    private fun onStandardMathActionPressed(actionId: Int) {
        if (actionId == ButtonsIds.PLUS || actionId == ButtonsIds.MINUS || actionId == ButtonsIds.DIVISION || actionId == ButtonsIds.MULTIPLY) {
            with(model) {
                when (state) {
                    State.FirstArgInput -> {
                        firstArg = argumentToDouble()
                        state = State.SecondArgInput
                        expression.append(argument)
                        argument.setLength(DEFAULT_VALUE_LENGTH)
                        setZeroValueToArgument()
                    }
                    State.SecondArgInput -> {
                        onEqualsButtonPressed(actionId)
                        expression.deleteCharAt(expression.length - 1)
                    }
                    State.ResultShow -> {
                        firstArg = argumentToDouble()
                        state = State.SecondArgInput
                        expression.setLength(DEFAULT_VALUE_LENGTH)
                        expression.append(argument)
                        argument.setLength(DEFAULT_VALUE_LENGTH)
                    }
                }
                when (actionId) {
                    ButtonsIds.PLUS -> {
                        actionSelected = ButtonsIds.PLUS
                        expression.append("+")
                    }
                    ButtonsIds.MINUS -> {
                        actionSelected = ButtonsIds.MINUS
                        expression.append("-")
                    }
                    ButtonsIds.MULTIPLY -> {
                        actionSelected = ButtonsIds.MULTIPLY
                        expression.append("×")
                    }
                    ButtonsIds.DIVISION -> {
                        actionSelected = ButtonsIds.DIVISION
                        expression.append("÷")
                    }
                    else -> {}
                }
            }
        }
    }

    //Каст аргумента из строки в Double
    private fun argumentToDouble(): Double = model.argument.toString().toDouble()

    //Установка пустому аргументу значения по умолчанию "0"
    private fun setDefaultValueToEmptyArgument() {
        if (model.argument.isEmpty()) {
            setZeroValueToArgument()
        }
    }

    //Установка аргументу значения "0"
    private fun setZeroValueToArgument() {
        model.argument.append(DEFAULT_ZERO_VALUE)
    }

    //Откидываем нули после запятой, если число целое
    private fun doublePrime(number: Double): String {
        var result = ""
        if (number % 1 == 0.0) {
            result += number.toLong()
        } else {
            result += number
        }
        return result
    }

    private fun showResult() {
        view.showResult(model.getResultValue())
    }

    private fun showExpression() {
        view.showExpression(model.getExpressionValue())
    }

    companion object {
        const val ARG_MAX_LENGTH = 15
        const val DEFAULT_ZERO_VALUE = "0"
        const val DOUBLE_ZERO_VALUE = "00"
        const val DEFAULT_VALUE_LENGTH = 0
    }
}