package com.octacore.instagramapp.profile.model

data class UserModel(val id: String,
                     val username: String,
                     val profile_picture: String,
                     val counts: CountModel)