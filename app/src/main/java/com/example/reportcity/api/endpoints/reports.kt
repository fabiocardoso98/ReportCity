package com.example.reportcity.api.endpoints

import com.example.reportcity.api.entities.allReports
import com.example.reportcity.api.entities.reports
import retrofit2.Call
import retrofit2.http.*

interface reports {
/*
    @FormUrlEncoded
    @POST("users/login")
    fun login(@Field( "username") username: String?, @Field("password") password: String?): Call<reports>
*/

    @GET("reports")
    fun getAllReports(): Call<List<allReports>>

    @GET("reports/{id}")
    fun getReport(@Path("id") id: Int): Call<reports>

}