package com.octacore.instagramapp.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.octacore.instagramapp.R
import com.octacore.instagramapp.model.AuthModel
import com.octacore.instagramapp.network.ApiCallHandler
import com.octacore.instagramapp.repository.LoginRepository.getAccessToken
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.USER_ID
import com.octacore.instagramapp.utils.PreferencesUtil.getPreferenceString
import com.octacore.instagramapp.utils.PreferencesUtil.initPreference
import com.octacore.instagramapp.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initPreference(this)

        if(getPreferenceString(ACCESS_TOKEN) != null){
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

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
        webView.requestFocus()
        webView.loadUrl(mViewModel.requestUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url!!.contains("?code=")) {
                    val uri = Uri.parse(url)
                    Log.i("LoginActivity", "Uri: $uri")
                    val decodedUri = uri.encodedQuery
                    Log.i("LoginActivity", "Encoded Uri: $decodedUri")
                    mViewModel.accessCode = decodedUri!!.substring(decodedUri.lastIndexOf("=") + 1)
                    Log.i("LoginActivity", "Access Code: ${mViewModel.accessCode}")

                    getAccessToken(mViewModel.appId, mViewModel.clientSecret, mViewModel.redirectUrl, mViewModel.accessCode, object : ApiCallHandler{
                        override fun done() {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun success(data: Any) {
                            val model = data as AuthModel
                            val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                            intent.putExtra(ACCESS_TOKEN, model.access_token)
                            intent.putExtra(USER_ID, model.user_id)
                            startActivity(intent)
                            finish()
                        }
                    })
                }
                return false
            }
        }
    }
}