package com.octacore.instagramapp.profile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.octacore.instagramapp.AppPreferences
import com.octacore.instagramapp.AppPreferences.ACCESS_TOKEN
import com.octacore.instagramapp.AppPreferences.getPreferenceString
import com.octacore.instagramapp.R
import com.octacore.instagramapp.login.view.LoginActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppPreferences.initPreference(this)

        if (getPreferenceString(ACCESS_TOKEN) == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}