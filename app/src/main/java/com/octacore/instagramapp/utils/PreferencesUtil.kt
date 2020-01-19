package com.octacore.instagramapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.octacore.instagramapp.BaseApplication
import java.io.File


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
    const val LOGIN_STATUS = "login_status"

    fun initPreference(context: Context){
        preferences = context.getSharedPreferences(
            APP_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getPreference(key: String, value: String): String? {
        return preferences.getString(key, value)
    }

    fun getPreference(key: String, value: Boolean): Boolean?{
        return preferences.getBoolean(key, value)
    }

    fun putPreference(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putPreference(key: String, value: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearPreferences(context: Context) {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        context.cacheDir.deleteRecursively()
    }
}