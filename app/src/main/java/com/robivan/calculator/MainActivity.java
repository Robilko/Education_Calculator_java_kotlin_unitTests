package com.robivan.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private CalculatorModel calculator;
    private TextView display, resultScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] numberIds = new int[] {
                R.id.zero,
                R.id.double_zero,
                R.id.one,
                R.id.two,
                R.id.three,
                R.id.four,
                R.id.five,
                R.id.six,
                R.id.seven,
                R.id.eight,
                R.id.nine,
                R.id.dot
        };

        int[] actionIds = new int[] {
                R.id.plus,
                R.id.minus,
                R.id.multiply,
                R.id.division,
                R.id.percent,
                R.id.plus_minus,
                R.id.equals,
                R.id.backspace,
                R.id.clear
        };

        initResultScreen();

        calculator = new CalculatorModel();

        View.OnClickListener numberButtonClickListener = v -> {
            calculator.onNumPressed(v.getId());
            display.setText(calculator.getExpressionValue());
            resultScreen.setText(calculator.getResultValue());
        };

        View.OnClickListener actionButtonClickListener = v -> {
            calculator.onActionPressed(v.getId());
            display.setText(calculator.getExpressionValue());
            resultScreen.setText(calculator.getResultValue());
        };

        for (int numberId : numberIds) {
            findViewById(numberId).setOnClickListener(numberButtonClickListener);
        }

        for (int actionId : actionIds) {
            findViewById(actionId).setOnClickListener(actionButtonClickListener);
        }

    }
    private void initResultScreen() {
        display = findViewById(R.id.expression);
        resultScreen= findViewById(R.id.result);
    }
}