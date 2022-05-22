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
    private fun resetForNewExpressionIfPreviousIsFinished() {
        if (model.state == State.ResultShow) {
            model.expression.setLength(0)
            model.argument.setLength(0)
            model.state = State.FirstArgInput
        }
    }

    //Очистка значения от нуля в начале, если это цифра первого порядка в аргументе
    private fun clearZeroEqualsArgument() {
        if (model.argument.length == 1 && model.argument[0] == '0') {
            model.argument.setLength(0)
        }
    }

    //Обработка нажатия кнопки "00"
    private fun onDoubleZeroButtonPressed() {
        if (model.argument.isEmpty()) {
            model.argument.append("0")
        } else {
            model.argument.append("00")
        }
    }

    //Обработка нажатия кнопки "."
    private fun onDotButtonPressed() {
        if (model.argument.isEmpty()) {
            model.argument.append("0.")
        } else if (!model.argument.toString().contains(".")) {
            model.argument.append(".")
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
            else -> onPlusOrMinusOrDivisionOrMultiplyButtonsPressed(actionId)
        }
        showResult()
        showExpression()
    }

    //Обработка нажатия кнопки очистки
    private fun onClearButtonPressed() {
        model.argument.setLength(0)
        model.expression.setLength(0)
        model.state = State.ResultShow
        setZeroValueToArgument()
    }

    //Обработка нажатия кнопки "±"
    private fun onPlusMinusButtonPressed() {
        val tmp = argumentToDouble() * -1
        model.argument.setLength(0)
        model.argument.append(doublePrime(tmp))
    }

    //Обработка нажатия кнопки очистки последнего символа
    private fun onBackspaceButtonPressed() {
        model.argument.deleteCharAt(model.argument.length - 1)
        setDefaultValueToEmptyArgument()
    }

    //Обработка нажатия кнопки "%"
    private fun onPercentButtonPressed() {
        setDefaultValueToEmptyArgument()
        val tmp = argumentToDouble() / 100
        model.argument.setLength(0)
        model.argument.append(doublePrime(tmp))
    }

    //Обработка нажатия кнопки "="
    private fun onEqualsButtonPressed(actionId: Int) {
        if (model.state == State.FirstArgInput) {
            model.firstArg = argumentToDouble()
            model.state = State.SecondArgInput
            model.actionSelected = ButtonsIds.EQUALS
        }
        if (model.state == State.SecondArgInput) {
            model.secondArg = argumentToDouble()
            model.state = State.ResultShow
            model.expression.append(model.argument)
            model.expression.append("=")
            model.argument.setLength(0)

            when (model.actionSelected) {
                ButtonsIds.PLUS -> model.argument.append(doublePrime(model.firstArg + model.secondArg))
                ButtonsIds.MINUS -> model.argument.append(doublePrime(model.firstArg - model.secondArg))
                ButtonsIds.MULTIPLY -> model.argument.append(doublePrime(model.firstArg * model.secondArg))
                ButtonsIds.DIVISION -> if (model.secondArg == 0.0) {
                    view.showError()
                } else {
                    model.argument.append(doublePrime(model.firstArg / model.secondArg))
                }
                ButtonsIds.EQUALS -> model.argument.append(doublePrime(model.firstArg))
            }
        }
        onPlusOrMinusOrDivisionOrMultiplyButtonsPressed(actionId)
    }

    //Обработка нажатия кнопок: "+", "-", "×", "÷"
    private fun onPlusOrMinusOrDivisionOrMultiplyButtonsPressed(actionId: Int) {
        if (actionId == ButtonsIds.PLUS || actionId == ButtonsIds.MINUS || actionId == ButtonsIds.DIVISION || actionId == ButtonsIds.MULTIPLY) {
            when (model.state) {
                State.FirstArgInput -> {
                    model.firstArg = argumentToDouble()
                    model.state = State.SecondArgInput
                    model.expression.append(model.argument)
                    model.argument.setLength(0)
                    setZeroValueToArgument()
                }
                State.SecondArgInput -> {
                    onEqualsButtonPressed(actionId)
                    model.expression.deleteCharAt(model.expression.length - 1)
                }
                State.ResultShow -> {
                    model.firstArg = argumentToDouble()
                    model.state = State.SecondArgInput
                    model.expression.setLength(0)
                    model.expression.append(model.argument)
                    model.argument.setLength(0)
                }
            }
            when (actionId) {
                ButtonsIds.PLUS -> {
                    model.actionSelected = ButtonsIds.PLUS
                    model.expression.append("+")
                }
                ButtonsIds.MINUS -> {
                    model.actionSelected = ButtonsIds.MINUS
                    model.expression.append("-")
                }
                ButtonsIds.MULTIPLY -> {
                    model.actionSelected = ButtonsIds.MULTIPLY
                    model.expression.append("×")
                }
                ButtonsIds.DIVISION -> {
                    model.actionSelected = ButtonsIds.DIVISION
                    model.expression.append("÷")
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
    }
}