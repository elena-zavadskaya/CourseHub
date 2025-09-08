package com.example.coursehub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.coursehub.databinding.ActivityMainBinding
import com.example.coursehub.navigation.AppNavigator
import com.example.core.navigation.Navigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), CustomBottomNavigationView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        (navigator as AppNavigator).setNavController(navController)

        val bottomNavView = findViewById<CustomBottomNavigationView>(R.id.bottom_navigation)

        bottomNavView.addMenuItem(
            R.id.coursesFragment,
            R.drawable.ic_courses_inactive,
            R.drawable.ic_courses_active,
            "Главная"
        )
        bottomNavView.addMenuItem(
            R.id.favoritesFragment,
            R.drawable.ic_favorites_inactive,
            R.drawable.ic_favorites_active,
            "Избранное"
        )
        bottomNavView.addMenuItem(
            R.id.accountFragment,
            R.drawable.ic_account_inactive,
            R.drawable.ic_account_active,
            "Аккаунт"
        )

        bottomNavView.buildMenu()
        bottomNavView.setOnItemSelectedListener(this)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    bottomNavView.visibility = View.GONE
                }
                else -> {
                    bottomNavView.visibility = View.VISIBLE
                    bottomNavView.setSelectedItem(destination.id)
                }
            }
        }
    }

    override fun onItemSelected(itemId: Int) {
        when (itemId) {
            R.id.coursesFragment -> navigator.navigateToCourses()
            R.id.favoritesFragment -> navigator.navigateToFavorites()
            R.id.accountFragment -> navigator.navigateToAccount()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.coursesFragment, R.id.favoritesFragment, R.id.accountFragment -> {
                finish()
            }
            else -> super.onBackPressed()
        }
    }
}