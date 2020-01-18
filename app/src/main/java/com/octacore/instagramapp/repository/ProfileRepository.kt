package com.octacore.instagramapp.repository

import android.util.Log
import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.getPreference
import com.octacore.instagramapp.network.ProfileApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ProfileRepository {
    private val TAG = ProfileRepository::class.java.simpleName
    private val service = ProfileApiService.createService()

    fun getUserDetails(userID: String, accessToken: String, handler: ApiCallHandler){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getUserDataAsync(userID, "id,username,media_count", accessToken)
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

    fun getUserMedia(userID: String, accessToken: String, handler: ApiCallHandler){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getUserMediaAsync(userID, accessToken)
            try{
                val response = request.await()
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                } else{
                    Log.i(TAG, "Not successful")
                    handler.failed("Request not successful", response.errorBody().toString())
                }
            } catch (err: Exception){
                err.printStackTrace()
                handler.failed("Error in request", err.message!!)
            }
        }
    }

    fun getSingleMedia(mediaId: String){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getSingleMediaAsync(mediaId, "id,media_type,media_url,username,timestamp", getPreference(ACCESS_TOKEN, "")!!)
            try {
                val response = request.await()
                Log.i(TAG, "Single Media Data: $response")
                if (response.isSuccessful){
                    val body = response.body()
                }
            } catch (err: Exception){
                err.printStackTrace()
            }
        }
    }
}