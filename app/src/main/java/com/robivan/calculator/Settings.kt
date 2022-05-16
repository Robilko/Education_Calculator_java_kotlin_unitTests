package com.robivan.calculator;

public class Settings {
    private final String PREF_NAME;
    private final String KEY_THEME;
    private final String KEY_DARK_THEME;
    private final String KEY_LIGHT_THEME;


    public Settings() {
        this.PREF_NAME = "Settings.pref";
        this.KEY_THEME = "theme";
        this.KEY_DARK_THEME = "dark";
        this.KEY_LIGHT_THEME = "light";
    }


    public String getPREF_NAME() {
        return PREF_NAME;
    }

    public String getKEY_THEME() {
        return KEY_THEME;
    }

    public String getKEY_DARK_THEME() {
        return KEY_DARK_THEME;
    }

    public String getKEY_LIGHT_THEME() {
        return KEY_LIGHT_THEME;
    }
}
