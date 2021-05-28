package com.robivan.calculator;


import java.io.Serializable;

public class CalculatorModel implements Serializable {
    private double firstArg;
    private boolean hasDot = false;
    private final int ARG_MAX_LENGTH = 20;
    private final String DIV_BY_ZERO = "Разделить на ноль нельзя";

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

        if (ARGUMENT.length() < ARG_MAX_LENGTH) {
            if (ARGUMENT.length() == 1 && ARGUMENT.charAt(0) == '0') {
                ARGUMENT.setLength(0);
            }
            switch (buttonId) {
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
                case NumberButton.ZERO:
                    ARGUMENT.append("0");
                    break;
                case NumberButton.DOUBLE_ZERO:
                    if (ARGUMENT.length() == 0) {
                        ARGUMENT.append("0");
                    } else {
                        ARGUMENT.append("00");
                    }
                    break;
                case NumberButton.DOT:
                    if (ARGUMENT.length() == 0) {
                        ARGUMENT.append("0.");
                        hasDot = true;
                    } else if (!hasDot) {
                        ARGUMENT.append(".");
                        hasDot = true;
                    }
                    break;
                case  ActionButton.PERCENT:
                    if (ARGUMENT.length() == 0) {
                        ARGUMENT.append("0");
                    }
                        double tmp = Double.parseDouble(ARGUMENT.toString()) / 100;
                        if (tmp % 1 != 0) {
                            hasDot = true;
                        }
                        ARGUMENT.setLength(0);
                        ARGUMENT.append(doublePrimeToInt(tmp));
                    break;
            }
        }

    }

    public void onActionPressed(int actionId) {
        hasDot = false;
        double tmp = 0;
        if (ARGUMENT.length() == 0) {
            ARGUMENT.append("0");
        }

        switch (actionId) {
            case ActionButton.CLEAR:
                ARGUMENT.setLength(0);
                EXPRESSION.setLength(0);
                state = State.resultShow;
                ARGUMENT.append("0");
                break;

            case ActionButton.PLUS_MINUS:
                tmp = Double.parseDouble(ARGUMENT.toString()) * -1;
                ARGUMENT.setLength(0);
                ARGUMENT.append(doublePrimeToInt(tmp));
                break;

            case ActionButton.BACKSPACE:
                ARGUMENT.deleteCharAt(ARGUMENT.length() - 1);
                if (ARGUMENT.length() == 0) {
                    ARGUMENT.append("0");
                }
                break;

            case ActionButton.EQUALS:
                if (state == State.firstArgInput) {
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
                            ARGUMENT.append(doublePrimeToInt(firstArg + secondArg));
                            break;
                        case ActionButton.MINUS:
                            ARGUMENT.append(doublePrimeToInt(firstArg - secondArg));
                            break;
                        case ActionButton.MULTIPLY:
                            ARGUMENT.append(doublePrimeToInt(firstArg * secondArg));
                            break;
                        case ActionButton.DIVISION:
                            if (secondArg == 0) {
                                ARGUMENT.append(DIV_BY_ZERO);
                            } else {
                                ARGUMENT.append(doublePrimeToInt(firstArg / secondArg));
                            }
                            break;
                        case ActionButton.EQUALS:
                            ARGUMENT.append(doublePrimeToInt(firstArg));
                            break;
                    }
                }

            default:
                if (actionId == ActionButton.PLUS || actionId == ActionButton.MINUS || actionId == ActionButton.DIVISION || actionId == ActionButton.MULTIPLY) {
                    if (state == State.firstArgInput) {
                        firstArg = Double.parseDouble(ARGUMENT.toString());
                        state = State.secondArgInput;
                        EXPRESSION.append(ARGUMENT);
                        ARGUMENT.setLength(0);
                        ARGUMENT.append("0");
                    } else if (state == State.secondArgInput) {
                        EXPRESSION.deleteCharAt(EXPRESSION.length() - 1);
                    } else if (state == State.resultShow) {
                        if (ARGUMENT.toString().equals(DIV_BY_ZERO)) {
                            ARGUMENT.setLength(0);
                            ARGUMENT.append("0");
                        }
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

    }

    public String getResultValue() {
        return ARGUMENT.toString();
    }

    public String getExpressionValue() {
        return EXPRESSION.toString();
    }

    private String doublePrimeToInt(double number) { //TODO переделать метод без каста в int
        String result = "";
        if (number % 1 == 0)
            result +=(int)number;
        else
            result +=number;
        return result;
    }

}
