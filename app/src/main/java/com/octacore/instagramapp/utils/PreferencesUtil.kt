package com.octacore.instagramapp.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {

    private const val APP_PREFERENCE_FILE_NAME = "userdata"
    private lateinit var preferences : SharedPreferences
    const val ACCESS_TOKEN = "access_token"
    const val USER_ID = "user_id"
    const val MEDIA_ID = "media_id"
    const val USERNAME = "username"
    const val MEDIA_COUNT = "media_count"
    const val TIMESTAMP = "timestamp"
    const val MEDIA_TYPE = "media_type"
    const val MEDIA_URL = "media_url"
    const val USER_MEDIA = "user_media"
    const val AUTH_DATA = "auth_data"
    const val USER_DETAILS = "user_details"

    fun initPreference(context: Context){
        preferences = context.getSharedPreferences(
            APP_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
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