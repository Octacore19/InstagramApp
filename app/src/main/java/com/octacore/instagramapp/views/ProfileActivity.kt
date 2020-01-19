package com.octacore.instagramapp.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.octacore.instagramapp.R
import com.octacore.instagramapp.adapters.ProfileAdapter
import com.octacore.instagramapp.utils.GridItemDecoration
import com.octacore.instagramapp.utils.PreferencesUtil.clearPreferences
import com.octacore.instagramapp.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var mViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        toolbar.title = "Nothing"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(toolbar)
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        mViewModel.getAccessToken(intent)

        mViewModel.username.observe(this, Observer { username ->
            usernameTextview.text = username
            toolbar.title = username
        })

        mViewModel.mediaCount.observe(this, Observer { mediaCount ->
            mediaCountTextview.text = mediaCount
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.mediaIDs.observe(this, Observer { mediaIDs ->
            media_recycler.adapter = ProfileAdapter(mediaIDs, mViewModel.accessToken, this)
            media_recycler.layoutManager = GridLayoutManager(this, 2)
            media_recycler.addItemDecoration(GridItemDecoration(8, 2))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            startActivity(Intent(this, LoginActivity::class.java))
            clearPreferences()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}