package com.example.jobsearch

import android.app.Application

class AppContext : Application() {

    companion object {
        lateinit var appContext: AppContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

}