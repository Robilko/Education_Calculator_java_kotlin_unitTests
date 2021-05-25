package com.robivan.calculator;

public class CalculatorModel {
    private double firstArg;
    private double secondArg;
    private boolean hasDot = false;

    private StringBuilder inputStr = new StringBuilder();
    private StringBuilder history = new StringBuilder();

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
            history.setLength(0);
            inputStr.setLength(0);
            state = State.firstArgInput;
        }

        if (inputStr.length() < 10) {
            switch (buttonId) {
                case R.id.zero:
                    if (inputStr.length() != 0) {
                        inputStr.append("0");
                    }
                    break;
                case R.id.double_zero:
                    if (inputStr.length() != 0) {
                        inputStr.append("00");
                    }
                    break;
                case R.id.one:
                    inputStr.append("1");
                    break;
                case R.id.two:
                    inputStr.append("2");
                    break;
                case R.id.three:
                    inputStr.append("3");
                    break;
                case R.id.four:
                    inputStr.append("4");
                    break;
                case R.id.five:
                    inputStr.append("5");
                    break;
                case R.id.six:
                    inputStr.append("6");
                    break;
                case R.id.seven:
                    inputStr.append("7");
                    break;
                case R.id.eight:
                    inputStr.append("8");
                    break;
                case R.id.nine:
                    inputStr.append("9");
                    break;
                case R.id.dot:
                    if (inputStr.length() != 0 && !hasDot) {
                        inputStr.append(".");
                        hasDot = true;
                    }
                    break;
            }
        }

    }

    public void onActionPressed(int actionId) {
        hasDot = false;

        if (actionId == R.id.clear) {
            inputStr.setLength(0);
            history.setLength(0);
            state = State.resultShow;
            inputStr.append("0.0");
            return;
        }

        if (actionId == R.id.plus_minus) {
//            TODO
        }

        if (actionId == R.id.backspace) {   //TODO удаляет первый раз всю строку результата, а не посимвольно
            if (inputStr.length() != 0) {
                inputStr.deleteCharAt(inputStr.length() - 1);
            }
            if (history.length() != 0) {
                history.deleteCharAt(history.length() - 1);
            }
        }

        if (actionId == R.id.percent && inputStr.length() != 0 && state == State.firstArgInput) {
            firstArg = Double.parseDouble(inputStr.toString()) / 100;
            state = State.secondArgInput;
            inputStr.setLength(0);
        } else if (actionId == R.id.percent && inputStr.length() != 0 && state == State.secondArgInput) {
            secondArg = Double.parseDouble(inputStr.toString()) / 100;
            state = State.resultShow;
            inputStr.setLength(0);
        }
        if (actionId == R.id.equals && state == State.firstArgInput) {
            firstArg = Double.parseDouble(inputStr.toString());
            state = State.secondArgInput;
            actionSelected = actionId;
        }
        if (actionId == R.id.equals && state == State.secondArgInput) {
            secondArg = Double.parseDouble(inputStr.toString());
            state = State.resultShow;
            history.append(inputStr);
            history.append("=");
            inputStr.setLength(0);
            switch (actionSelected) {
                case R.id.plus:
                    inputStr.append(firstArg + secondArg);
                    break;
                case R.id.minus:
                    inputStr.append(firstArg - secondArg);
                    break;
                case R.id.multiply:
                    inputStr.append(firstArg * secondArg);
                    break;
                case R.id.division:
                    if (firstArg == 0 || secondArg == 0) {
                        inputStr.append(0);
                    } else {
                        inputStr.append(firstArg / secondArg);
                    }
                    break;
                case R.id.percent:
                    //TODO
                    break;
                case R.id.equals:
                    inputStr.append(firstArg);
                    break;
            }

        } else {
            if (state == State.firstArgInput){
                firstArg = Double.parseDouble(inputStr.toString());
                state = State.secondArgInput;
                history.append(inputStr);
                inputStr.setLength(0);
            } else if (state == State.secondArgInput) {
                history.deleteCharAt(history.length() - 1);
            } else if (state == State.resultShow) {
                firstArg = Double.parseDouble(inputStr.toString());
                state = State.secondArgInput;
                history.setLength(0);
                history.append(inputStr);
                inputStr.setLength(0);
            }

            switch (actionId) {
                case R.id.plus:
                    actionSelected = R.id.plus;
                    history.append("+");
                    break;
                case R.id.minus:
                    actionSelected = R.id.minus;
                    history.append("-");
                    break;
                case R.id.multiply:
                    actionSelected = R.id.multiply;
                    history.append("×");
                    break;
                case R.id.division:
                    actionSelected = R.id.division;
                    history.append("÷");
                    break;
                case R.id.percent:
                    actionSelected = R.id.percent;
                    history.append("%");
                    break;
            }

        }

    }

    public String getValue() {
        return inputStr.toString();
    }

    public String getHistory() {
        return history.toString();
    }
}
