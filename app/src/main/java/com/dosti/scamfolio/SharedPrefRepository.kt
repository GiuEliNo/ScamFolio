package com.dosti.scamfolio

import android.content.Context
import androidx.preference.PreferenceManager
class SharedPrefRepository(context : Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun putUsr(key: String, username: String) {
        prefs.edit().putString(key, username).apply()
    }

    fun getUsr(key: String, default: String) = prefs?.getString(key, default) ?: default
}