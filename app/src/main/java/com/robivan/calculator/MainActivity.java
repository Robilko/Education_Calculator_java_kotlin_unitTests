package com.robivan.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CalculatorModel calculator;
    private TextView display, resultScreen;
    protected final String CALC_KEY = MainActivity.class.getCanonicalName() + "calc_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

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



        display = findViewById(R.id.expression);
        resultScreen= findViewById(R.id.result);

        resultScreen.setText("0");

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

        for (int numberId : Util.NUMBER_IDS) {
            findViewById(numberId).setOnClickListener(numberButtonClickListener);
        }

        for (int actionId : Util.ACTION_IDS) {
            findViewById(actionId).setOnClickListener(actionButtonClickListener);
        }
    }
}