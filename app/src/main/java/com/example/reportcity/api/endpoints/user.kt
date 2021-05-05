package com.example.reportcity.api.endpoints

import com.example.reportcity.api.entities.users
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface user {

    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field( "username") username: String?, @Field("password") password: String?): Call<users>

}