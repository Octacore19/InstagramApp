package com.octacore.instagramapp.repository

import android.util.Log
import com.google.gson.Gson
import com.octacore.instagramapp.network.ApiCallHandler
import com.octacore.instagramapp.network.LoginApiService
import com.octacore.instagramapp.utils.PreferencesUtil.AUTH_DATA
import com.octacore.instagramapp.utils.PreferencesUtil.putPreferenceString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LoginRepository {
    private val TAG = LoginRepository::class.java.simpleName
    private val service = LoginApiService.createService()

    fun getAccessToken(clientId: String, clientSecret: String, redirectUrl: String, accessCode: String, handler: ApiCallHandler){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getAccessTokenAsync(clientId, clientSecret, "authorization_code", redirectUrl, accessCode)
            try {
                val response = request.await()
                handler.done()
                Log.i(TAG, "Response: $response")
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                    val gson = Gson()
                    val authData = gson.toJson(body)
                    putPreferenceString(AUTH_DATA, authData)
                } else{
                    Log.i(TAG, "Get Access Token Not successful")
                    handler.failed("Request not successful", response.errorBody().toString())
                }
            } catch (err: Exception){
                err.printStackTrace()
                handler.failed("Error in request", err.message!!)
            }
        }
    }
}