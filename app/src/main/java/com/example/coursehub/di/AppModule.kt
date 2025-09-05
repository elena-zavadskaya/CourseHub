package com.example.coursehub.di

import com.example.core.data.repository.CourseRepositoryImpl
import com.example.core.navigation.Navigator
import com.example.core.data.repository.CourseRepository
import com.example.coursehub.navigation.AppNavigator
import com.example.courses.presentation.list.presentation.CoursesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Навигация
    single<Navigator> { AppNavigator(androidContext()) }

    // Репозиторий курсов
    single<CourseRepository> { CourseRepositoryImpl() }

    // ViewModel для списка курсов
    viewModel { CoursesViewModel(get()) }
}

// Если у вас есть сетевые или другие зависимости, добавьте их здесь
val networkModule = module {
    // Пример: Retrofit сервис
    // single { provideRetrofit(get()) }
    // single { provideCourseService(get()) }
}

val databaseModule = module {
    // Пример: Room база данных
    // single { provideDatabase(androidContext()) }
    // single { provideCourseDao(get()) }
}