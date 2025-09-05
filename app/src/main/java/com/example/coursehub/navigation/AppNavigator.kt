package com.example.coursehub.navigation

import android.content.Context
import androidx.navigation.NavController
import com.example.core.navigation.Navigator
import com.example.coursehub.R

class AppNavigator(private val context: Context) : Navigator {

    private var navController: NavController? = null

    fun setNavController(controller: NavController) {
        this.navController = controller
    }

    override fun navigateToCourses() {
        navController?.navigate(R.id.action_global_coursesFragment)
    }

    override fun navigateToLogin() {
        navController?.navigate(R.id.action_global_loginFragment)
    }

    override fun navigateToFavorites() {
        navController?.navigate(R.id.action_global_favoritesFragment)
    }

    override fun navigateToAccount() {
        navController?.navigate(R.id.action_global_accountFragment)
    }

    override fun navigateBack() {
        navController?.popBackStack()
    }
}