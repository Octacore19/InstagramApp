package com.octacore.instagramapp.handlers

import android.app.Activity
import android.content.Context

interface AccessCodehandler {
    fun success(data: String, context: Activity)
}