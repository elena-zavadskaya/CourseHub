// app/src/main/java/com/example/coursehub/di/appModule.kt
package com.example.coursehub.di

import com.example.core.data.repository.CourseRepository
import com.example.core.domain.interactor.GetCoursesInteractor
import com.example.coursehub.data.CourseRepositoryImpl
import com.example.core.navigation.Navigator
import com.example.coursehub.navigation.AppNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Навигация
    single<Navigator> { AppNavigator(androidContext()) }

    // Репозиторий курсов
    single<CourseRepository> { CourseRepositoryImpl(get()) }

    // Interactors
    factory { GetCoursesInteractor(get()) }
}