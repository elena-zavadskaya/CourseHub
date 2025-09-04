package com.example.coursehub

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CourseHubApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация Koin
        startKoin {
            androidContext(this@CourseHubApp)
            // modules(ваши модули) - добавим позже, когда будем настраивать DI
        }
    }
}