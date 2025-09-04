package com.example.coursehub.di

import com.example.core.navigation.Navigator
import com.example.coursehub.navigation.AppNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { AppNavigator(androidContext()) }
}