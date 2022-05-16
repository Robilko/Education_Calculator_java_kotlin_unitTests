package com.robivan.calculator;


import java.io.Serializable;

public class CalculatorModel implements Serializable {
    private double firstArg, secondArg;

    private final StringBuilder argument = new StringBuilder();
    private final StringBuilder expression = new StringBuilder();

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
            expression.setLength(0);
            argument.setLength(0);
            state = State.firstArgInput;
        }

        int ARG_MAX_LENGTH = 15;
        if (argument.length() < ARG_MAX_LENGTH) {
            if (argument.length() == 1 && argument.charAt(0) == '0') {
                argument.setLength(0);
            }

            if (buttonId == Util.DOUBLE_ZERO) {
                if (argument.length() == 0) {
                    argument.append("0");
                } else {
                    argument.append("00");
                }
            }else if (buttonId == Util.DOT) {
                if (argument.length() == 0) {
                    argument.append("0.");
                } else if (!argument.toString().contains(".")) {
                    argument.append(".");
                }
            } else {
                for (int i = 0; i < Util.NUMBER_IDS.length; i++) {
                    if (buttonId == Util.NUMBER_IDS[i]) {
                        argument.append(i);
                    }
                }
            }
        }

    }

    public void onActionPressed(int actionId) {
        double tmp;
        if (argument.length() == 0) {
            argument.append("0");
        }

        switch (actionId) {
            case Util.CLEAR:
                argument.setLength(0);
                expression.setLength(0);
                state = State.resultShow;
                argument.append("0");
                break;

            case Util.PLUS_MINUS:
                tmp = Double.parseDouble(argument.toString()) * -1;
                argument.setLength(0);
                argument.append(doublePrime(tmp));
                break;

            case Util.BACKSPACE:
                argument.deleteCharAt(argument.length() - 1);
                if (argument.length() == 0) {
                    argument.append("0");
                }
                break;

            case  Util.PERCENT:
                if (argument.length() == 0) {
                    argument.append("0");
                }
                tmp = Double.parseDouble(argument.toString()) / 100;
                argument.setLength(0);
                argument.append(doublePrime(tmp));
                break;

            case Util.EQUALS:
                if (state == State.firstArgInput) {
                    firstArg = Double.parseDouble(argument.toString());
                    state = State.secondArgInput;
                    actionSelected = Util.EQUALS;
                }
                if (state == State.secondArgInput) {
                    secondArg = Double.parseDouble(argument.toString());
                    state = State.resultShow;
                    expression.append(argument);
                    expression.append("=");
                    argument.setLength(0);
                    switch (actionSelected) {
                        case Util.PLUS:
                            argument.append(doublePrime(firstArg + secondArg));
                            break;
                        case Util.MINUS:
                            argument.append(doublePrime(firstArg - secondArg));
                            break;
                        case Util.MULTIPLY:
                            argument.append(doublePrime(firstArg * secondArg));
                            break;
                        case Util.DIVISION:
                            if (secondArg == 0) {
                                throw new NullPointerException();
                            } else {
                                argument.append(doublePrime(firstArg / secondArg));
                            }
                            break;
                        case Util.EQUALS:
                            argument.append(doublePrime(firstArg));
                            break;
                    }
                }

            default:
                if (actionId == Util.PLUS || actionId == Util.MINUS || actionId == Util.DIVISION || actionId == Util.MULTIPLY) {
                    if (state == State.firstArgInput) {
                        firstArg = Double.parseDouble(argument.toString());
                        state = State.secondArgInput;
                        expression.append(argument);
                        argument.setLength(0);
                        argument.append("0");
                    } else if (state == State.secondArgInput) {
                        expression.deleteCharAt(expression.length() - 1);
                    } else if (state == State.resultShow) {
                        firstArg = Double.parseDouble(argument.toString());
                        state = State.secondArgInput;
                        expression.setLength(0);
                        expression.append(argument);
                        argument.setLength(0);
                    }

                    switch (actionId) {
                        case Util.PLUS:
                            actionSelected = Util.PLUS;
                            expression.append("+");
                            break;
                        case Util.MINUS:
                            actionSelected = Util.MINUS;
                            expression.append("-");
                            break;
                        case Util.MULTIPLY:
                            actionSelected = Util.MULTIPLY;
                            expression.append("×");
                            break;
                        case Util.DIVISION:
                            actionSelected = Util.DIVISION;
                            expression.append("÷");
                            break;
                    }
                }
        }

    }

    public String getResultValue() {
        return argument.toString();
    }

    public String getExpressionValue() {
        return expression.toString();
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
