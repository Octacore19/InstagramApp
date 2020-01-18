package com.octacore.instagramapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(app: Application): AndroidViewModel(app) {
    var userId = ""
    var username = ""
    var mediaCount = ""
}