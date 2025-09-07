package com.example.coursehub.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.data.repository.CourseRepository
import com.example.core.data.repository.FavoriteRepository
import com.example.core.domain.interactor.GetCoursesInteractor
import com.example.coursehub.data.repository.CourseRepositoryImpl
import com.example.coursehub.data.repository.FavoriteRepositoryImpl
import com.example.core.navigation.Navigator
import com.example.coursehub.navigation.AppNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

val appModule = module {
    single<Navigator> { AppNavigator(androidContext()) }

    single { androidContext().dataStore }

    single<CourseRepository> { CourseRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }

    single { provideApiService(get()) }

    factory { GetCoursesInteractor(get()) }
}