package com.octacore.instagramapp.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.octacore.instagramapp.utils.PreferencesUtil.ACCESS_TOKEN
import com.octacore.instagramapp.utils.PreferencesUtil.MEDIA_COUNT
import com.octacore.instagramapp.utils.PreferencesUtil.USERNAME
import com.octacore.instagramapp.utils.PreferencesUtil.USER_ID
import com.octacore.instagramapp.utils.PreferencesUtil.USER_MEDIA
import com.octacore.instagramapp.utils.PreferencesUtil.getPreferenceString
import com.octacore.instagramapp.utils.PreferencesUtil.initPreference
import com.octacore.instagramapp.R
import com.octacore.instagramapp.repository.ProfileRepository.getSingleMedia
import com.octacore.instagramapp.repository.ProfileRepository.getUserDetails
import com.octacore.instagramapp.repository.ProfileRepository.getUserMedia
import com.octacore.instagramapp.model.UserMediaModel
import com.octacore.instagramapp.model.UserModel
import com.octacore.instagramapp.network.ApiCallHandler
import com.octacore.instagramapp.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {
    private val TAG = ProfileActivity::class.java.simpleName

    private lateinit var mViewModel: ProfileViewModel
    private val accessToken by lazy { intent.getStringExtra(ACCESS_TOKEN) }
    private val userId by lazy { intent.getStringExtra(USER_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPreference(this)
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        getUserDetails(userId, accessToken, object:ApiCallHandler{
            override fun done() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun success(data: Any) {
                val model = data as UserModel
                displayUserDetails(model)
            }
        })
    }

    private fun displayUserDetails(user: UserModel) {
        mViewModel.userId = user.id
        mViewModel.username = user.username
        mViewModel.mediaCount = user.media_count
        Log.i("ProfileActivity", "UserID: ${mViewModel.userId}")
        usernameTextview.text = mViewModel.username
        mediaCountTextview.text = mViewModel.mediaCount
    }

    private fun getUserMedia(index : Int) : String{
        val gson = Gson()
        val media = getPreferenceString(USER_MEDIA)
        val mediaObject = gson.fromJson(media, UserMediaModel::class.java)
        return mediaObject.data[index].id
    }
}