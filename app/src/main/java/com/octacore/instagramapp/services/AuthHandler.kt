package com.octacore.instagramapp.services

import android.os.Handler
import android.os.Message
import com.octacore.instagramapp.views.LoginActivity
import java.lang.ref.WeakReference

class AuthHandler(private val activity: WeakReference<LoginActivity>): Handler() {

    override fun handleMessage(msg: Message) {
        if (msg.what == 0) {
            activity.get()
            sendEmptyMessageDelayed(0, 1000)
        }
    }
}