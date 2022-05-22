package com.robivan.calculator.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioButton
import android.widget.RadioGroup
import android.os.Bundle
import com.robivan.calculator.util.ThemeSettings
import com.robivan.calculator.databinding.ActivitySettingsBinding
import com.robivan.calculator.util.ThemeSettings.KEY_LIGHT_THEME
import com.robivan.calculator.util.ThemeSettings.KEY_THEME
import com.robivan.calculator.util.ThemeSettings.KEY_THEME_SETTINGS_RADIOBUTTON_INDEX
import com.robivan.calculator.util.setApplicationTheme

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var radioGroup: RadioGroup
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialization()
    }

    private fun initialization() {
        sharedPreferences  = getSharedPreferences(ThemeSettings.PREF_NAME, MODE_PRIVATE)
        with(binding) {
            radioGroup = changeThemeButtons
            returnButton.setOnClickListener { finish() }
            radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener)
        }
        loadSavedPreferences()
    }

    private var radioGroupOnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedId)
            val checkedIndex = radioGroup.indexOfChild(checkedRadioButton)
            saveThemeMode(checkedRadioButton, checkedIndex)
            loadSavedPreferences()
        }

    private fun saveThemeMode(checkedRadioButton: RadioButton, checkedIndex: Int) {
        sharedPreferences.edit().putInt(KEY_THEME_SETTINGS_RADIOBUTTON_INDEX, checkedIndex)
            .apply()

        if (checkedRadioButton == binding.radioButtonLightTheme) {
            sharedPreferences.edit().putString(KEY_THEME, KEY_LIGHT_THEME).apply()
        } else if (checkedRadioButton == binding.radioButtonDarkTheme) {
            sharedPreferences.edit().putString(KEY_THEME, ThemeSettings.KEY_DARK_THEME).apply()
        }
    }

    private fun loadSavedPreferences() {
        setSavedCheckedRadioButton()
        setApplicationTheme(this@SettingsActivity)
    }

    private fun setSavedCheckedRadioButton() {
        val defaultThemeRadioButtonIndex = radioGroup.indexOfChild(binding.radioButtonLightTheme)
        val savedCheckedRadioButtonIndex: Int =
            if (sharedPreferences.contains(KEY_THEME_SETTINGS_RADIOBUTTON_INDEX)) {
                sharedPreferences.getInt(
                    KEY_THEME_SETTINGS_RADIOBUTTON_INDEX,
                    defaultThemeRadioButtonIndex
                )
            } else {
                defaultThemeRadioButtonIndex
            }

        val savedCheckedRadioButton =
            radioGroup.getChildAt(savedCheckedRadioButtonIndex) as RadioButton?
        savedCheckedRadioButton?.isChecked = true
    }
}