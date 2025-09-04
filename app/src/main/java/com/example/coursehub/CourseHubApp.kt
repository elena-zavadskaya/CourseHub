package com.example.coursehub

import android.app.Application
import com.example.auth.di.authModule
import com.example.coursehub.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CourseHubApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CourseHubApp)
            modules(appModule, authModule)
        }
    }
}