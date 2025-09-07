// features/favorites/src/main/java/com/example/favorites/di/favoritesModule.kt
package com.example.favorites.di

import com.example.favorites.presentation.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel() }
}