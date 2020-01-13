package com.octacore.instagramapp.profile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.octacore.instagramapp.AppPreferences
import com.octacore.instagramapp.AppPreferences.ACCESS_TOKEN
import com.octacore.instagramapp.AppPreferences.USER_STATE
import com.octacore.instagramapp.AppPreferences.getPreferenceString
import com.octacore.instagramapp.R
import com.octacore.instagramapp.login.view.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppPreferences.initPreference(this)

        if (getPreferenceString(USER_STATE) == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        if (intent != null && intent.getStringExtra("access_token")!!.isNotEmpty()){
            val token = intent.getStringExtra(ACCESS_TOKEN)!!
            loadUserProfile(token)
        }
    }

    private fun loadUserProfile(token: String){

    }
}