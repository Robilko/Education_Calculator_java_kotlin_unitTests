package com.robivan.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {
    private Settings settings;
    RadioButton lightThemeBtn, darkThemeBtn;
    RadioGroup radioGroup;
    public static final String APP_PREFERENCES = "mySettings";
    final String KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnReturn = findViewById(R.id.return_button);
        btnReturn.setOnClickListener(v -> {
            finish();
        });

        settings = new Settings();
        radioGroup = findViewById(R.id.change_theme_buttons);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
        loadPreferences();
    }

    private void saveThemeMode(String theme) {
        getSharedPreferences(settings.getPREF_NAME(), MODE_PRIVATE).edit().putString(settings.getKEY_THEME(), theme).apply();
    }

    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            lightThemeBtn = findViewById(R.id.radio_button_light_theme);
            darkThemeBtn = findViewById(R.id.radio_button_dark_theme);

            RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(checkedId);
            if (checkedRadioButton.equals(lightThemeBtn)) {
                saveThemeMode(settings.getKEY_LIGHT_THEME());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (checkedRadioButton.equals(darkThemeBtn)) {
                saveThemeMode(settings.getKEY_DARK_THEME());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
            getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit().putInt(KEY_RADIOBUTTON_INDEX,checkedIndex).apply();
        }
    };

    private void loadPreferences() {
        int savedRadioIndex = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(KEY_RADIOBUTTON_INDEX, 0);
        RadioButton savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);
    }
}