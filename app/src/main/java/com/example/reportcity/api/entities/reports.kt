package com.example.reportcity.api.entities

data class reports (
        val id: Int,
        val name: String,
        val description: String,
        val image: String,
        val lat: Double,
        val lng: Double,
        val morada: String,
        val users_id: Int
)

data class  allReports (
        val reports: reports,
        val users: users
)
