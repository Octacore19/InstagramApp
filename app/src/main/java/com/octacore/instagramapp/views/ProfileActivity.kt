package com.octacore.instagramapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.octacore.instagramapp.R
import com.octacore.instagramapp.adapters.ProfileAdapter
import com.octacore.instagramapp.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private val TAG = ProfileActivity::class.java.simpleName
    private lateinit var mViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mViewModel.getAccessToken(intent)

        mViewModel.username.observe(this, Observer { username ->
            usernameTextview.text = username
        })

        mViewModel.mediaCount.observe(this, Observer { mediaCount ->
            mediaCountTextview.text = mediaCount
        })
/*
        media_recycler.adapter = ProfileAdapter(mViewModel.mediaIDs!!, mViewModel.accessToken, this)
        media_recycler.layoutManager = GridLayoutManager(this, 3)*/
    }

    override fun onResume() {
        super.onResume()
        mViewModel.mediaIDs.observe(this, Observer { mediaIDs ->
            media_recycler.adapter = ProfileAdapter(mediaIDs, mViewModel.accessToken, this)
            media_recycler.layoutManager = GridLayoutManager(this, 3)
        })
    }
}