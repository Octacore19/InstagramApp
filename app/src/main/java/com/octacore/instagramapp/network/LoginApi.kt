package com.octacore.instagramapp.network

import com.octacore.instagramapp.model.AuthModel
import kotlinx.coroutines.Deferred
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