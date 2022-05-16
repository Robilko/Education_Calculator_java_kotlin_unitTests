package com.robivan.calculator

import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioButton
import android.widget.RadioGroup
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.robivan.calculator.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            radioGroup = changeThemeButtons
            returnButton.setOnClickListener { finish() }
            radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener)
        }
        loadPreferences()
    }

    private fun saveThemeMode(theme: String) {
        getSharedPreferences(Settings.pREF_NAME, MODE_PRIVATE).edit().putString(Settings.kEY_THEME, theme).apply()
    }

    private var radioGroupOnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton.equals(binding.radioButtonLightTheme)) {
                saveThemeMode(Settings.kEY_LIGHT_THEME)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else if (checkedRadioButton.equals(binding.radioButtonDarkTheme)) {
                saveThemeMode(Settings.kEY_DARK_THEME)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            val checkedIndex = radioGroup.indexOfChild(checkedRadioButton)
            getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).edit()
                .putInt(KEY_RADIOBUTTON_INDEX, checkedIndex).apply()
        }

    private fun loadPreferences() {
        val savedRadioIndex = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(
            KEY_RADIOBUTTON_INDEX, 0
        )
        val savedCheckedRadioButton = radioGroup.getChildAt(savedRadioIndex) as RadioButton?
        savedCheckedRadioButton?.isChecked = true
    }

    companion object {
        const val APP_PREFERENCES = "mySettings"
        const val KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX"
    }
}