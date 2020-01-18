package com.octacore.instagramapp.network

import com.octacore.instagramapp.model.MediaModel
import com.octacore.instagramapp.model.UserMediaModel
import com.octacore.instagramapp.model.UserModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {
    @GET("/{user_id}")
    fun getUserDataAsync(@Path("user_id") userId: String,
                         @Query("fields") fields: String,
                         @Query("access_token") accessToken: String): Deferred<Response<UserModel>>

    @GET("/{user_id}/media")
    fun getUserMediaAsync(@Path("user_id") userId: String,
                          @Query("access_token") accessToken: String): Deferred<Response<UserMediaModel>>

    @GET("/{media_id}")
    fun getSingleMediaAsync(@Path("media_id") mediaId: String,
                            @Query("fields") fields: String,
                            @Query("access_token") accessToken: String): Deferred<Response<MediaModel>>
}