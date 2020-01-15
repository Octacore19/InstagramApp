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
import androidx.lifecycle.ViewModelProviders
import com.octacore.instagramapp.AppPreferences.putPreferenceString
import com.octacore.instagramapp.R
import com.octacore.instagramapp.login.LoginRepository
import com.octacore.instagramapp.login.network.LoginApiService
import com.octacore.instagramapp.login.viewmodel.LoginViewModel
import com.octacore.instagramapp.profile.view.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(){
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginBtn.setOnClickListener {
            displayUrlOnWebView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayUrlOnWebView(){
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
        webView.loadUrl(mViewModel.requestUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (url.contains("?code=")) {
                    val uri = Uri.parse(url)
                    val decodedUri = uri.encodedQuery
                    mViewModel.accessCode = decodedUri!!.substring(decodedUri.lastIndexOf("=") + 1)
                    mViewModel.token = LoginRepository.getAccessToken(mViewModel.appId, mViewModel.clientSecret, mViewModel.redirectUrl, mViewModel.accessCode)
                    putPreferenceString("access_token", mViewModel.token)
                    startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                    finish()
                }
            }
        }
    }
}
