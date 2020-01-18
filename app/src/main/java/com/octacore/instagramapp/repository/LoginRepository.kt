package com.octacore.instagramapp.repository

import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.network.LoginApiService
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
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                } else{
                    handler.failed("Request not successful", response.errorBody().toString())
                }
            } catch (err: Exception){
                handler.failed("Error in request", err.message!!)
            }
        }
    }
}