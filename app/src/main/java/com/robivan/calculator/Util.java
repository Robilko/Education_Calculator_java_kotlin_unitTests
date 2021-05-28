package com.robivan.calculator;

public abstract class Util {
    static int DOUBLE_ZERO = R.id.double_zero;
    static int DOT = R.id.dot;

    static final int[] NUMBER_IDS = new int[] {
            R.id.zero,
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine,
            DOUBLE_ZERO,
            DOT
        };

    static final int PLUS = R.id.plus;
    static final int MINUS = R.id.minus;
    static final int PLUS_MINUS = R.id.plus_minus;
    static final int MULTIPLY = R.id.multiply;
    static final int DIVISION = R.id.division;
    static final int PERCENT = R.id.percent;
    static final int EQUALS = R.id.equals;
    static final int CLEAR = R.id.clear;
    static final int BACKSPACE = R.id.backspace;


    static final int[] ACTION_IDS = new int[] {
            PLUS,
            MINUS,
            MULTIPLY,
            DIVISION,
            PLUS_MINUS,
            EQUALS,
            BACKSPACE,
            PERCENT,
            CLEAR
        };
}
