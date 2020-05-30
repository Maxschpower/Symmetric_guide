package com.maxsch.symmetricguide.data.datasource.settings

import android.content.SharedPreferences

class SettingsDataSource(
    val prefs: SharedPreferences
) {

    private companion object {
        const val FONT_KEY = "font_key"
    }

    var fontSize: Int
        get() = prefs.getInt(FONT_KEY, 40)
        set(value) {
            prefs.edit().putInt(FONT_KEY, value).apply()
        }
}