package com.robivan.calculator.presenter

import com.robivan.calculator.model.CalculatorModel

interface MainContract {
    interface MainView {
        fun showResult(resultValue: String)
        fun showExpression(expressionValue: String)
        fun showError()
    }

    interface MainPresenter {
        fun onAttach(model: CalculatorModel, view: MainView)
        fun onDetach(): CalculatorModel
        fun onNumPressed(buttonId: Int)
        fun onActionPressed(actionId: Int)
    }
}