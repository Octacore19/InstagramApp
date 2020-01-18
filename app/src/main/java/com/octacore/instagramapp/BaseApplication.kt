package com.octacore.instagramapp

import android.app.Application
import com.octacore.instagramapp.utils.PreferencesUtil.initPreference

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initPreference(this)
    }
}