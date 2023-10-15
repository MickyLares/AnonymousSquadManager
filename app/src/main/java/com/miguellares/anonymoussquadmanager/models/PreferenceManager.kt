package com.miguellares.anonymoussquadmanager.models

import android.content.Context

class PreferenceManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}