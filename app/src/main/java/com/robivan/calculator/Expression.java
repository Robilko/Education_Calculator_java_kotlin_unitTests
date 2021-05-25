package com.robivan.calculator;


public class Expression  {
    private StringBuilder stringBuilder;

    public Expression() {
        stringBuilder = new StringBuilder();
    }

    public String getValue() {
        return stringBuilder.toString();
    }

    public void setValue(String value) {
        stringBuilder.append(value);
    }

    public char lastChar() {
        return stringBuilder.charAt(stringBuilder.length() - 1);
    }

    public void backspace() {
        int size = stringBuilder.length();
        stringBuilder.deleteCharAt(size - 1);
    }

    public void clear() {
        stringBuilder = new StringBuilder();
    }


}
