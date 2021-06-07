package com.robivan.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CalculatorModel calculator;
    private TextView display, resultScreen;
    private Settings settings;
    protected final String CALC_KEY = MainActivity.class.getCanonicalName() + "calc_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheme();
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
        AppCompatImageButton btnSettings = findViewById(R.id.settings);
        calculator = new CalculatorModel();
        settings = new Settings();


        display = findViewById(R.id.expression);
        resultScreen= findViewById(R.id.result);

        resultScreen.setText("0");

        btnSettings.setOnClickListener(v -> {
            Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(runSettings);
        });

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

    private void setTheme() {
        String themeValue = getSharedPreferences(settings.getPREF_NAME(), MODE_PRIVATE)
                .getString(settings.getKEY_THEME(), null);
        if (themeValue != null && !themeValue.isEmpty()) {
            if (themeValue.equals(settings.getKEY_DARK_THEME())){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}