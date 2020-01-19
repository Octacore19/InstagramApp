package com.octacore.instagramapp.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.octacore.instagramapp.R
import com.octacore.instagramapp.handlers.AccessCodehandler
import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.model.AuthModel
import com.octacore.instagramapp.repository.LoginRepository.getAccessToken
import com.octacore.instagramapp.utils.PreferencesUtil
import com.octacore.instagramapp.utils.PreferencesUtil.LOGIN_STATUS
import com.octacore.instagramapp.utils.PreferencesUtil.putPreference
import com.octacore.instagramapp.views.ProfileActivity

class LoginViewModel(application: Application) : AndroidViewModel(application), AccessCodehandler {
    private val TAG = LoginViewModel::class.java.simpleName
    private var appId: String = application.resources.getString(R.string.app_id)
    private var clientSecret = application.resources.getString(R.string.client_secret)
    private var redirectUrl = application.resources.getString(R.string.redirect_url)
    private var baseUrl = application.resources.getString(R.string.base_url)
    var requestUrl = baseUrl+"oauth/authorize/?client_id="+appId+"&redirect_uri="+redirectUrl+"&scope=user_profile,user_media&response_type=code"
    private lateinit var accessCode: String

    override fun success(data: String, context: Activity) {
        accessCode = data
        getAccessToken(appId, clientSecret, redirectUrl, accessCode, object : ApiCallHandler {
            override fun success(data: Any) {
                moveToActivity(data, context)
            }
            override fun failed(title: String, reason: String) {
                Log.e(TAG, title + reason)
            }
        })
    }

    private fun moveToActivity(data: Any, context: Activity){
        val model = data as AuthModel
        val intent = Intent(context, ProfileActivity::class.java)
        intent.putExtra(PreferencesUtil.ACCESS_TOKEN, model.access_token)
        intent.putExtra(PreferencesUtil.USER_ID, model.user_id)
        context.startActivity(intent)
        putPreference(LOGIN_STATUS, true)
        context.finish()
    }
}