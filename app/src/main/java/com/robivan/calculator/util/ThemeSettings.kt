package com.robivan.calculator.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.robivan.calculator.util.ThemeSettings.KEY_DARK_THEME
import com.robivan.calculator.util.ThemeSettings.KEY_LIGHT_THEME
import com.robivan.calculator.util.ThemeSettings.KEY_SYSTEM_THEME

object ThemeSettings {
    const val PREF_NAME = "Settings.pref"
    const val KEY_THEME = "theme"
    const val KEY_DARK_THEME = "dark"
    const val KEY_LIGHT_THEME = "light"
    const val KEY_SYSTEM_THEME = "system"
    const val KEY_THEME_SETTINGS_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX"
}

internal fun setApplicationTheme(context: Context) {
    context.getSharedPreferences(ThemeSettings.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        .getString(ThemeSettings.KEY_THEME, null)?.let { themeValue ->
            when (themeValue) {
                KEY_DARK_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                KEY_LIGHT_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                KEY_SYSTEM_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
}