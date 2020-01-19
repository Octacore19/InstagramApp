package com.octacore.instagramapp.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.model.MediaID
import com.octacore.instagramapp.model.UserMediaModel
import com.octacore.instagramapp.model.UserModel
import com.octacore.instagramapp.repository.ProfileRepository.getUserDetails
import com.octacore.instagramapp.repository.ProfileRepository.getUserMedia
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.USER_ID

class ProfileViewModel(app: Application): AndroidViewModel(app) {
    private val TAG = ProfileViewModel::class.java.simpleName
    private var userId = ""
    var accessToken = ""
    val username = MutableLiveData<String>()
    val mediaCount = MutableLiveData<String>()
    val mediaIDs = MutableLiveData<List<MediaID>>()

    init {
        username.value = ""
        mediaCount.value = ""
        mediaIDs.value = listOf()
    }

    fun getAccessToken(intent: Intent){
        accessToken = intent.getStringExtra(ACCESS_TOKEN)!!
        userId = intent.getStringExtra(USER_ID)!!
        getUserDetails(userId, accessToken, object : ApiCallHandler{
            override fun success(data: Any) {
                val model = data as UserModel
                username.value = model.username
                mediaCount.value = model.media_count
                getUserMedia()
            }

            override fun failed(title: String, reason: String) {
                Log.e(TAG, title + reason)
            }
        })
    }

    private fun getUserMedia(){
        getUserMedia(userId, accessToken, object : ApiCallHandler{
            override fun success(data: Any) {
                val model = data as UserMediaModel
                mediaIDs.value = model.data
                Log.i(TAG, "Media data: ${(mediaIDs.value as ArrayList<MediaID>)[0]}")
            }

            override fun failed(title: String, reason: String) {
                Log.e(TAG, title + reason)
            }

        })
    }
}