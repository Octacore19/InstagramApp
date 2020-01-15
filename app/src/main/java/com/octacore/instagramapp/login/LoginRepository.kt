package com.octacore.instagramapp.login

import android.util.Log
import com.octacore.instagramapp.login.network.LoginApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LoginRepository {
    private val TAG = LoginRepository::class.java.simpleName
    private val service = LoginApiService.createService()

    fun getAccessToken(clientId: String, clientSecret: String, redirectUrl: String, accessCode: String): String{
        var token = ""
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getAccessTokenAsync(clientId, clientSecret, "authorization_code", redirectUrl, accessCode)
            try {
                val response = request.await()
                Log.i(TAG, "Response: $response")
                if (response.isSuccessful){
                    val body = response.body()
                    token = body!!.access_token
                    Log.i(TAG, "Access Token: $token")
                } else{
                    Log.i(TAG, "Not successful")
                }
            } catch (err: Exception){
                err.printStackTrace()
            }
        }
        return token
    }
}