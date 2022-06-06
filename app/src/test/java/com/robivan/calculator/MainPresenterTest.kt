package com.robivan.calculator

import com.robivan.calculator.model.CalculatorModel
import com.robivan.calculator.presenter.MainContract
import com.robivan.calculator.presenter.MainPresenter
import com.robivan.calculator.util.ButtonsIds
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    private lateinit var presenter: MainPresenter
    private lateinit var model: CalculatorModel

    @Mock
    private lateinit var view: MainContract.MainView

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = MainPresenter()
        model = CalculatorModel()

        presenter.onAttach(model, view)
    }

    @Test
    fun onAttach_Test() {
        verify(view, times(1)).showResult(model.getResultValue())
        verify(view, times(1)).showExpression(model.getExpressionValue())
    }

    @Test
    fun onDetach_Test() {
        assertEquals(presenter.onDetach(), model)
    }

    @Test //Double zero button
    fun onNumPressed_doubleZeroButton_Test() {
        doAssertEqualsTestToNumberButton(ButtonsIds.DOUBLE_ZERO, "0")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[8])
        doAssertEqualsTestToNumberButton(ButtonsIds.DOUBLE_ZERO, "800")
    }

    @Test //Dot button
    fun onNumPressed_dotButton_Test() {
        doAssertEqualsTestToNumberButton(ButtonsIds.DOT, "0.")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[9])
        doAssertEqualsTestToNumberButton(ButtonsIds.DOT, "0.9")
    }

    @Test //Buttons with numbers
    fun onNumPressed_numbersButtons_Test() {
        var actualValue: String? = null
        for (i in 0..9) {
            when (i) {
                0, 1 -> actualValue = i.toString()
                else -> actualValue += i.toString()
            }
            doAssertEqualsTestToNumberButton(ButtonsIds.NUMBER_IDS[i], actualValue!!)
        }
    }

    @Test //Plus button
    fun onActionPressed_plusButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[1])
        doAssertEqualsTestToActionButton(ButtonsIds.PLUS, "0", "1+")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])
        doAssertEqualsTestToActionButton(ButtonsIds.PLUS, "0", "3+")
    }
    @Test //Minus button
    fun onActionPressed_minusButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[3])
        doAssertEqualsTestToActionButton(ButtonsIds.MINUS, "0", "3-")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[1])
        doAssertEqualsTestToActionButton(ButtonsIds.MINUS, "0", "2-")
    }
    @Test //Multiply button
    fun onActionPressed_multiplyButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])
        doAssertEqualsTestToActionButton(ButtonsIds.MULTIPLY, "0", "2×")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[3])
        doAssertEqualsTestToActionButton(ButtonsIds.MULTIPLY, "0", "6×")
    }
    @Test //Division button
    fun onActionPressed_divisionButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])
        doAssertEqualsTestToActionButton(ButtonsIds.DIVISION, "0", "2÷")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[4])
        doAssertEqualsTestToActionButton(ButtonsIds.DIVISION, "0", "0.5÷")
    }
    @Test //Clear button
    fun onActionPressed_clearButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])
        doAssertEqualsTestToActionButton(ButtonsIds.CLEAR, "0", "0")
    }
    @Test //Plus_minus button
    fun onActionPressed_plusMinusButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[1])
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])

        doAssertEqualsTestToActionButton(ButtonsIds.PLUS_MINUS, "-12", "0")
        doAssertEqualsTestToActionButton(ButtonsIds.PLUS_MINUS, "12", "0")
    }
    @Test //Backspace button
    fun onActionPressed_backspaceButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[1])
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[2])
        doAssertEqualsTestToActionButton(ButtonsIds.BACKSPACE, "1", "0")
        doAssertEqualsTestToActionButton(ButtonsIds.BACKSPACE, "0", "0")
    }
    @Test //Percent button
    fun onActionPressed_percentButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[9])
        doAssertEqualsTestToActionButton(ButtonsIds.PERCENT, "0.09", "0")
    }
    @Test //Equals button
    fun onActionPressed_equalsButton_Test() {
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[9])
        presenter.onActionPressed(ButtonsIds.PLUS)
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[1])
        doAssertEqualsTestToActionButton(ButtonsIds.EQUALS, "10", "9+1=")
        presenter.onNumPressed(ButtonsIds.NUMBER_IDS[5])
        doAssertEqualsTestToActionButton(ButtonsIds.EQUALS, "5", "5=")
    }

    private fun doAssertEqualsTestToActionButton(buttonId: Int, actualResultValue: String, actualExpressionValue: String) {
        presenter.onActionPressed(buttonId)
        assertEquals(model.getResultValue(), actualResultValue)
        assertEquals(model.getExpressionValue(), actualExpressionValue)
    }

    private fun doAssertEqualsTestToNumberButton(buttonId: Int, actualResultValue: String) {
        presenter.onNumPressed(buttonId)
        assertEquals(model.getResultValue(), actualResultValue)
        assertEquals(model.getExpressionValue(), "0")
    }
}