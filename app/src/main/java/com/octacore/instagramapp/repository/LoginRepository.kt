package com.octacore.instagramapp.repository

import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.network.LoginApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LoginRepository {
    private val service = LoginApiService.createService()

    fun getAccessToken(clientId: String, clientSecret: String, redirectUrl: String, accessCode: String, handler: ApiCallHandler){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getAccessTokenAsync(clientId, clientSecret, "authorization_code", redirectUrl, accessCode)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                } else{
                    handler.failed("Fetching access token not successful: ", response.code().toString())
                }
            } catch (err: Exception){
                handler.failed("Error in fetching access token: ", err.message!!)
            }
        }
    }
}