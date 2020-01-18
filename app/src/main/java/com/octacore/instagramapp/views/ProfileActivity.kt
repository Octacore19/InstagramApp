package com.octacore.instagramapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.octacore.instagramapp.utils.PreferencesUtil.USER_MEDIA
import com.octacore.instagramapp.utils.PreferencesUtil.getPreference
import com.octacore.instagramapp.R
import com.octacore.instagramapp.model.UserMediaModel
import com.octacore.instagramapp.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {
    private val TAG = ProfileActivity::class.java.simpleName
    private lateinit var mViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mViewModel.getAccessToken(intent)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.username.observe(this, Observer { username ->
            usernameTextview.text = username
        })
        mViewModel.mediaCount.observe(this, Observer { mediaCount ->
            mediaCountTextview.text = mediaCount
        })
    }

    private fun getUserMedia(index : Int) : String{
        val gson = Gson()
        val media = getPreference(USER_MEDIA, "")
        val mediaObject = gson.fromJson(media, UserMediaModel::class.java)
        return mediaObject.data[index].id
    }
}