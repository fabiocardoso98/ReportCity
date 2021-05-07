package com.example.reportcity.api.endpoints

import com.example.reportcity.api.entities.*
import com.example.reportcity.api.entities.reports
import retrofit2.Call
import retrofit2.http.*

interface reports {


    @FormUrlEncoded
    @POST("report")
    fun setReport(@Field( "name") name: String, @Field("description") description: String, @Field( "image") image: String, @Field("lat") lat: String,
              @Field( "lng") lng: String, @Field("morada") morada: String,@Field( "users_id") users_id: String): Call<result>

    @FormUrlEncoded
    @PUT("reports/{id}")
    fun setUpdateReport(@Path("id") id: Int, @Field( "name") name: String, @Field("description") description: String, @Field( "image") image: String, @Field("lat") lat: String,
                        @Field( "lng") lng: String, @Field("morada") morada: String, @Field( "users_id") users_id: String): Call<result>

    @GET("reports")
    fun getAllReports(): Call<List<allReports>>

    @GET("reports/{id}")
    fun getReport(@Path("id") id: Int): Call<reports>

    @GET("reports/user/{id}")
    fun getReportUser(@Path("id") id: Int): Call<List<allReports>>

    @DELETE("reports/{id}")
    fun deleteReport(@Path("id") id: Int): Call<result>

}