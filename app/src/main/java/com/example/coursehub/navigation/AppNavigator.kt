package com.example.coursehub.navigation

import android.content.Context
import android.content.Intent
import com.example.core.navigation.Navigator
import com.example.coursehub.MainActivity

class AppNavigator(private val context: Context) : Navigator {
    override fun navigateToCourses() {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("destination", "courses")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("destination", "login")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }
}