package com.robivan.calculator;

public class CalculatorModel {
    private double firstArg;
    private boolean hasDot = false;

    private final StringBuilder ARGUMENT = new StringBuilder();
    private final StringBuilder EXPRESSION = new StringBuilder();

    private int actionSelected;

    private enum State {
        firstArgInput,
        secondArgInput,
        resultShow
    }

    private State state;

    public CalculatorModel() {
        state = State.firstArgInput;
    }

    public void onNumPressed(int buttonId) {
        if (state == State.resultShow) {
            EXPRESSION.setLength(0);
            ARGUMENT.setLength(0);
            state = State.firstArgInput;
        }

        if (ARGUMENT.length() < 10) {
            switch (buttonId) {
                case NumberButton.ZERO:
                    if (ARGUMENT.length() != 0) {
                        ARGUMENT.append("0");
                    }
                    break;
                case NumberButton.DOUBLE_ZERO:
                    if (ARGUMENT.length() != 0) {
                        ARGUMENT.append("00");
                    }
                    break;
                case NumberButton.ONE:
                    ARGUMENT.append("1");
                    break;
                case NumberButton.TWO:
                    ARGUMENT.append("2");
                    break;
                case NumberButton.THREE:
                    ARGUMENT.append("3");
                    break;
                case NumberButton.FOUR:
                    ARGUMENT.append("4");
                    break;
                case NumberButton.FIVE:
                    ARGUMENT.append("5");
                    break;
                case NumberButton.SIX:
                    ARGUMENT.append("6");
                    break;
                case NumberButton.SEVEN:
                    ARGUMENT.append("7");
                    break;
                case NumberButton.EIGHT:
                    ARGUMENT.append("8");
                    break;
                case NumberButton.NINE:
                    ARGUMENT.append("9");
                    break;
                case NumberButton.DOT:
                    if (ARGUMENT.length() != 0 && !hasDot) {
                        ARGUMENT.append(".");
                        hasDot = true;
                    }
                    break;
            }
        }

    }

    public void onActionPressed(int actionId) {
        hasDot = false;

        if (actionId == ActionButton.CLEAR) {
            ARGUMENT.setLength(0);
            EXPRESSION.setLength(0);
            state = State.resultShow;
            ARGUMENT.append("0.0");
            return;
        }

        if (actionId == ActionButton.PLUS_MINUS) {
            if (ARGUMENT.length() == 0) {
                return;
            }
            double tmp = Double.parseDouble(ARGUMENT.toString()) * -1;
            ARGUMENT.setLength(0);
            ARGUMENT.append(tmp);
            return;
        }

        if (actionId == ActionButton.BACKSPACE) {
            if (ARGUMENT.length() == 0) {
                return;
            }
            ARGUMENT.deleteCharAt(ARGUMENT.length() - 1);
            return;
        }

        if (actionId == ActionButton.PERCENT && ARGUMENT.length() != 0) {
            double tmp = Double.parseDouble(ARGUMENT.toString()) / (double)100;
            ARGUMENT.setLength(0);
            ARGUMENT.append(tmp);

            return;
        }

        if (actionId == ActionButton.EQUALS) {
            if (state == State.firstArgInput){
                firstArg = Double.parseDouble(ARGUMENT.toString());
                state = State.secondArgInput;
                actionSelected = ActionButton.EQUALS;
            }
            if (state == State.secondArgInput) {
                double secondArg = Double.parseDouble(ARGUMENT.toString());
                state = State.resultShow;
                EXPRESSION.append(ARGUMENT);
                EXPRESSION.append("=");
                ARGUMENT.setLength(0);
                switch (actionSelected) {
                    case ActionButton.PLUS:
                        ARGUMENT.append(firstArg + secondArg);
                        break;
                    case ActionButton.MINUS:
                        ARGUMENT.append(firstArg - secondArg);
                        break;
                    case ActionButton.MULTIPLY:
                        ARGUMENT.append(firstArg * secondArg);
                        break;
                    case ActionButton.DIVISION:
                        if (firstArg == 0 || secondArg == 0) {
                            ARGUMENT.append(0);
                        } else {
                            ARGUMENT.append(firstArg / secondArg);
                        }
                        break;
                    case ActionButton.EQUALS:
                        ARGUMENT.append(firstArg);
                        break;
                }
            }

        } else {
            if (state == State.firstArgInput){
                firstArg = Double.parseDouble(ARGUMENT.toString());
                state = State.secondArgInput;
                EXPRESSION.append(ARGUMENT);
                ARGUMENT.setLength(0);
            } else if (state == State.secondArgInput) {
                EXPRESSION.deleteCharAt(EXPRESSION.length() - 1);
            } else if (state == State.resultShow) {
                firstArg = Double.parseDouble(ARGUMENT.toString());
                state = State.secondArgInput;
                EXPRESSION.setLength(0);
                EXPRESSION.append(ARGUMENT);
                ARGUMENT.setLength(0);
            }

            switch (actionId) {
                case ActionButton.PLUS:
                    actionSelected = ActionButton.PLUS;
                    EXPRESSION.append("+");
                    break;
                case ActionButton.MINUS:
                    actionSelected = ActionButton.MINUS;
                    EXPRESSION.append("-");
                    break;
                case ActionButton.MULTIPLY:
                    actionSelected = ActionButton.MULTIPLY;
                    EXPRESSION.append("×");
                    break;
                case ActionButton.DIVISION:
                    actionSelected = ActionButton.DIVISION;
                    EXPRESSION.append("÷");
                    break;
            }

        }

    }

    public String getResultValue() {
        return ARGUMENT.toString();
    }

    public String getExpressionValue() {
        return EXPRESSION.toString();
    }
}
