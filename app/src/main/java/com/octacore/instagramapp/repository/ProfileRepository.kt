package com.octacore.instagramapp.repository

import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.network.ProfileApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ProfileRepository {
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
                    handler.failed("Fetching user details not successful: ", response.code().toString())
                }
            } catch (err: Exception){
                handler.failed("Error in fetching user details: ", err.message!!)
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
                    handler.failed("Fetching user media not successful: ", response.code().toString())
                }
            } catch (err: Exception){
                handler.failed("Error in fetching user media: ", err.message!!)
            }
        }
    }

    fun getSingleMedia(mediaId: String,accessToken: String, handler: ApiCallHandler){
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getSingleMediaAsync(mediaId, "id,media_type,media_url,username,timestamp", accessToken)
            try {
                val response = request.await()
                if (response.isSuccessful){
                    val body = response.body()
                    handler.success(body!!)
                } else{
                    handler.failed("Fetching a single media not successful: ", response.code().toString())
                }
            } catch (err: Exception){
                handler.failed("Error in fetching a single media: ", err.message!!)
            }
        }
    }
}