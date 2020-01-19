package com.octacore.instagramapp.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.octacore.instagramapp.views.LoginActivity


class AuthService: Service() {
    private var startTime: Long = 0
    private val endTime: Long = 0
    var isTimerRunning: Boolean = false
    private val NOTIFICATION_ID = 1

    private val serviceBinder: IBinder = RunServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onBind(intent: Intent?): IBinder? = serviceBinder

    fun startTimer(){
        if (!isTimerRunning){
            startTime = System.currentTimeMillis()
            isTimerRunning = true
        }
    }

    fun goForeground(){
        startForeground(NOTIFICATION_ID, createNotification())
    }

    fun goBackground() {
        stopForeground(true)
    }

    private fun createNotification(): Notification? {
        val builder = NotificationCompat.Builder(this)

        val resultIntent = Intent(this, LoginActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        return builder.build()
    }

    inner class RunServiceBinder : Binder() {
        fun getService(): AuthService = AuthService()
    }
}