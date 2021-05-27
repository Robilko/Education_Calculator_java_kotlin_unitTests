package com.robivan.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CalculatorModel calculator;
    private TextView display, resultScreen;
    private  int[] numberIds, actionIds;
    protected final String CALC_KEY = MainActivity.class.getCanonicalName() + "calc_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable(CALC_KEY, calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        if (instanceState.containsKey(CALC_KEY)) {
            calculator = (CalculatorModel) instanceState.getSerializable(CALC_KEY);
            display.setText(calculator.getExpressionValue());
            resultScreen.setText(calculator.getResultValue());
        }
    }

    private void initialization() {
        calculator = new CalculatorModel();

        numberIds = new int[] {
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

        actionIds = new int[] {
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

        display = findViewById(R.id.expression);
        resultScreen= findViewById(R.id.result);

        resultScreen.setText("0");
    }
}