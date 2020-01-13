package com.octacore.instagramapp.login.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.octacore.instagramapp.AppPreferences.ACCESS_TOKEN
import com.octacore.instagramapp.AppPreferences.USER_STATE
import com.octacore.instagramapp.AppPreferences.putPreferenceString
import com.octacore.instagramapp.R
import com.octacore.instagramapp.profile.view.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){
    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var appId: String
    private lateinit var redirectUrl: String
    private lateinit var baseUrl: String
    private lateinit var requestUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appId = resources.getString(R.string.app_id)
        redirectUrl = resources.getString(R.string.redirect_url)
        baseUrl = resources.getString(R.string.base_url)
        requestUrl = baseUrl+"oauth/authorize/?client_id="+appId+"&redirect_uri="+redirectUrl+"&scope=user_profile,user_media&response_type=code"

        loginBtn.setOnClickListener {
            login()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun login(){
        loginBtn.visibility = View.GONE
        webView.visibility = View.VISIBLE
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.requestFocus()
        webView.isScrollbarFadingEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(requestUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (url.contains("?code=")) {
                    val uri = Uri.parse(url)
                    val decodedUri = uri.encodedQuery
                    val accessToken = decodedUri!!.substring(decodedUri.lastIndexOf("=") + 1)
                    Log.i(TAG, "Auth Token: $accessToken")

                    putPreferenceString(USER_STATE, "saved")

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(ACCESS_TOKEN, accessToken)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
