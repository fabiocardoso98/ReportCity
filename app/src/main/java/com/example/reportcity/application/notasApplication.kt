package com.example.reportcity.application

import android.app.Application
import com.example.reportcity.database.reportyCityDatabase
import com.example.reportcity.database.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class notasApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { reportyCityDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { repository(database.NotasDao()) }
}