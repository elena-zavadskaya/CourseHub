package com.example.coursehub

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.coursehub.databinding.ActivityMainBinding
import com.example.coursehub.navigation.AppNavigator
import com.example.core.navigation.Navigator
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        // Добавляем элементы меню
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

        // Обработка изменений навигации
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

    private fun updateMenuIcons(bottomNavView: BottomNavigationView, selectedItemId: Int) {
        val menu = bottomNavView.menu

        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val isSelected = item.itemId == selectedItemId

            // Находим view элемента меню
            val itemView = bottomNavView.findViewById<View>(item.itemId)

            if (itemView != null) {
                // Удаляем стандартный фон (если есть)
                itemView.background = null

                // Ищем ImageView внутри элемента меню
                val iconView = findIconView(itemView)

                if (iconView != null) {
                    // Устанавливаем или убираем наш кастомный фон вокруг иконки
                    if (isSelected) {
                        iconView.setBackgroundResource(R.drawable.bottom_nav_icon_bg)
                        // Устанавливаем отступы для правильного размещения иконки
                        iconView.setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
                    } else {
                        iconView.background = null
                        iconView.setPadding(0, 0, 0, 0)
                    }
                }
            }

            // Обновляем иконку
            item.icon = when (item.itemId) {
                R.id.coursesFragment -> {
                    if (isSelected) ContextCompat.getDrawable(this, R.drawable.ic_courses_active)
                    else ContextCompat.getDrawable(this, R.drawable.ic_courses_inactive)
                }
                R.id.favoritesFragment -> {
                    if (isSelected) ContextCompat.getDrawable(this, R.drawable.ic_favorites_active)
                    else ContextCompat.getDrawable(this, R.drawable.ic_favorites_inactive)
                }
                R.id.accountFragment -> {
                    if (isSelected) ContextCompat.getDrawable(this, R.drawable.ic_account_active)
                    else ContextCompat.getDrawable(this, R.drawable.ic_account_inactive)
                }
                else -> null
            }
        }

        bottomNavView.invalidate()
    }

    // Вспомогательная функция для поиска ImageView (иконки) в hierarchy view
    private fun findIconView(view: View): ImageView? {
        if (view is ImageView) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                val result = findIconView(child)
                if (result != null) {
                    return result
                }
            }
        }

        return null
    }

    // Extension function для преобразования dp в px
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
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