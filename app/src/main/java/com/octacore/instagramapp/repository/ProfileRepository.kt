package com.octacore.instagramapp.repository

import android.util.Log
import com.google.gson.Gson
import com.octacore.instagramapp.network.ApiCallHandler
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.MEDIA_COUNT
import com.octacore.instagramapp.utils.PreferencesUtil.MEDIA_ID
import com.octacore.instagramapp.utils.PreferencesUtil.MEDIA_TYPE
import com.octacore.instagramapp.utils.PreferencesUtil.MEDIA_URL
import com.octacore.instagramapp.utils.PreferencesUtil.TIMESTAMP
import com.octacore.instagramapp.utils.PreferencesUtil.USERNAME
import com.octacore.instagramapp.utils.PreferencesUtil.USER_ID
import com.octacore.instagramapp.utils.PreferencesUtil.USER_MEDIA
import com.octacore.instagramapp.utils.PreferencesUtil.getPreferenceString
import com.octacore.instagramapp.utils.PreferencesUtil.putPreferenceString
import com.octacore.instagramapp.network.ProfileApiService
import com.octacore.instagramapp.utils.PreferencesUtil.USER_DETAILS
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
                Log.i(TAG, "Response: $response")
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                    val gson = Gson()
                    val userDetails = gson.toJson(body)
                    putPreferenceString(USER_DETAILS, userDetails)
                } else{
                    Log.i(TAG, "Get user details Not successful")
                    handler.failed("Request not successful", response.errorBody().toString())
                }
            } catch (err: Exception){
                err.printStackTrace()
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
                    val gson = Gson()
                    val media = gson.toJson(body)
                    Log.i(TAG, "Media: $media")
                    putPreferenceString(USER_MEDIA, media)
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
            val request = service.getSingleMediaAsync(mediaId, "id,media_type,media_url,username,timestamp", getPreferenceString(
                ACCESS_TOKEN
            )!!)
            try {
                val response = request.await()
                Log.i(TAG, "Single Media Data: $response")
                if (response.isSuccessful){
                    val body = response.body()
                    Log.i(TAG, "Single Media data: $body")
                    putPreferenceString(MEDIA_ID, body!!.id)
                    putPreferenceString(MEDIA_TYPE, body.media_type)
                    putPreferenceString(MEDIA_URL, body.media_url)
                    putPreferenceString(USERNAME, body.username)
                    putPreferenceString(TIMESTAMP, body.timestamp)
                }
            } catch (err: Exception){
                err.printStackTrace()
            }
        }
    }
}