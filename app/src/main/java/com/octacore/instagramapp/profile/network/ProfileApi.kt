package com.octacore.instagramapp.profile.network

import com.octacore.instagramapp.profile.model.UserModel
import retrofit2.http.GET

interface ProfileApi {
    @GET("/users/self")
    fun getUserData(): UserModel
}