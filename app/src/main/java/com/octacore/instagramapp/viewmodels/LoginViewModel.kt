package com.octacore.instagramapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.octacore.instagramapp.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var appId = application.resources.getString(R.string.app_id)
    var clientSecret = application.resources.getString(R.string.client_secret)
    var redirectUrl = application.resources.getString(R.string.redirect_url)
    var baseUrl = application.resources.getString(R.string.base_url)
    var requestUrl = baseUrl+"oauth/authorize/?client_id="+appId+"&redirect_uri="+redirectUrl+"&scope=user_profile,user_media&response_type=code"
    var accessCode = ""
}