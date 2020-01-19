package com.octacore.instagramapp.views

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.octacore.instagramapp.R
import com.octacore.instagramapp.services.AuthHandler
import com.octacore.instagramapp.services.AuthService
import com.octacore.instagramapp.services.AuthService.RunServiceBinder
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.USER_ID
import com.octacore.instagramapp.utils.PreferencesUtil.getPreference
import com.octacore.instagramapp.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){
    private var serviceBound: Boolean = false
    private lateinit var mViewModel: LoginViewModel
    private lateinit var authService: AuthService
    private lateinit var handler: AuthHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginBtn.setOnClickListener {
            displayUrlOnWebView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (getPreference(ACCESS_TOKEN, "") != null && getPreference(ACCESS_TOKEN, "") != ""){
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(ACCESS_TOKEN, getPreference(ACCESS_TOKEN, ""))
            intent.putExtra(USER_ID, getPreference(USER_ID, ""))
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayUrlOnWebView(){
        imageView.visibility = View.GONE
        loginBtn.visibility = View.GONE
        webView.visibility = View.VISIBLE
        webView.settings.javaScriptEnabled = true
        webView.requestFocus()
        webView.loadUrl(mViewModel.requestUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url!!.contains("?code=")) {
                    view!!.visibility = View.GONE
                    val uri = Uri.parse(url)
                    val decodedUri = uri.encodedQuery
                    mViewModel.success(decodedUri!!.substring(decodedUri.lastIndexOf("=") + 1), this@LoginActivity)
                }
                return false
            }
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as RunServiceBinder
            authService = binder.getService()
            serviceBound = true
            authService.goBackground()
            if (authService.isTimerRunning) {
//                updateUIStartRun()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }
}