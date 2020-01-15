package com.octacore.instagramapp

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private val APP_PREFERENCE_FILE_NAME = "userdata"
    private lateinit var preferences : SharedPreferences
    val ACCESS_TOKEN = "access_token"

    fun initPreference(context: Context){
        preferences = context.getSharedPreferences(APP_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getPreferenceString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun putPreferenceString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun clearPreferences() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}