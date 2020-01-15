package com.octacore.instagramapp.login.network

import com.octacore.instagramapp.login.model.AuthModel
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/oauth/access_token")
    fun getAccessTokenAsync(@Field("client_id") clientID: String,
                            @Field("client_secret") clientSecret: String,
                            @Field("grant_type") type: String,
                            @Field("redirect_uri") redirectURI: String,
                            @Field("code") code: String): Deferred<Response<AuthModel>>
}