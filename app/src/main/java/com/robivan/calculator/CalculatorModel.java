package com.robivan.calculator;


import java.io.Serializable;

public class CalculatorModel implements Serializable {
    private double firstArg, secondArg;
    String DIV_BY_ZERO = "Разделить на ноль нельзя";

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

        int ARG_MAX_LENGTH = 20;
        if (ARGUMENT.length() < ARG_MAX_LENGTH) {
            if (ARGUMENT.length() == 1 && ARGUMENT.charAt(0) == '0') {
                ARGUMENT.setLength(0);
            }

            if (buttonId == Util.DOUBLE_ZERO) {
                if (ARGUMENT.length() == 0) {
                    ARGUMENT.append("0");
                } else {
                    ARGUMENT.append("00");
                }
            }else if (buttonId == Util.DOT) {
                if (ARGUMENT.length() == 0) {
                    ARGUMENT.append("0.");
                } else if (!ARGUMENT.toString().contains(".")) {
                    ARGUMENT.append(".");
                }
            } else {
                for (int i = 0; i < Util.NUMBER_IDS.length; i++) {
                    if (buttonId == Util.NUMBER_IDS[i]) {
                        ARGUMENT.append(i);
                    }
                }
            }
        }

    }

    public void onActionPressed(int actionId) {
        double tmp;
        if (ARGUMENT.length() == 0) {
            ARGUMENT.append("0");
        }

        switch (actionId) {
            case Util.CLEAR:
                ARGUMENT.setLength(0);
                EXPRESSION.setLength(0);
                state = State.resultShow;
                ARGUMENT.append("0");
                break;

            case Util.PLUS_MINUS:
                tmp = Double.parseDouble(ARGUMENT.toString()) * -1;
                ARGUMENT.setLength(0);
                ARGUMENT.append(doublePrime(tmp));
                break;

            case Util.BACKSPACE:
                ARGUMENT.deleteCharAt(ARGUMENT.length() - 1);
                if (ARGUMENT.length() == 0) {
                    ARGUMENT.append("0");
                }
                break;

            case  Util.PERCENT:
                if (ARGUMENT.length() == 0) {
                    ARGUMENT.append("0");
                }
                tmp = Double.parseDouble(ARGUMENT.toString()) / 100;
                ARGUMENT.setLength(0);
                ARGUMENT.append(doublePrime(tmp));
                break;

            case Util.EQUALS:
                if (state == State.firstArgInput) {
                    firstArg = Double.parseDouble(ARGUMENT.toString());
                    state = State.secondArgInput;
                    actionSelected = Util.EQUALS;
                }
                if (state == State.secondArgInput) {
                    secondArg = Double.parseDouble(ARGUMENT.toString());
                    state = State.resultShow;
                    EXPRESSION.append(ARGUMENT);
                    EXPRESSION.append("=");
                    ARGUMENT.setLength(0);
                    switch (actionSelected) {
                        case Util.PLUS:
                            ARGUMENT.append(doublePrime(firstArg + secondArg));
                            break;
                        case Util.MINUS:
                            ARGUMENT.append(doublePrime(firstArg - secondArg));
                            break;
                        case Util.MULTIPLY:
                            ARGUMENT.append(doublePrime(firstArg * secondArg));
                            break;
                        case Util.DIVISION:
                            if (secondArg == 0) {
                                ARGUMENT.append(DIV_BY_ZERO);
                            } else {
                                ARGUMENT.append(doublePrime(firstArg / secondArg));
                            }
                            break;
                        case Util.EQUALS:
                            ARGUMENT.append(doublePrime(firstArg));
                            break;
                    }
                }

            default:
                if (actionId == Util.PLUS || actionId == Util.MINUS || actionId == Util.DIVISION || actionId == Util.MULTIPLY) {
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
                        case Util.PLUS:
                            actionSelected = Util.PLUS;
                            EXPRESSION.append("+");
                            break;
                        case Util.MINUS:
                            actionSelected = Util.MINUS;
                            EXPRESSION.append("-");
                            break;
                        case Util.MULTIPLY:
                            actionSelected = Util.MULTIPLY;
                            EXPRESSION.append("×");
                            break;
                        case Util.DIVISION:
                            actionSelected = Util.DIVISION;
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

    private String doublePrime(double number) {
        String result = "";
        if (number % 1 == 0) {
            result += (long) number;
        }
        else {
            result += number;
        }
        return result;
    }

}
