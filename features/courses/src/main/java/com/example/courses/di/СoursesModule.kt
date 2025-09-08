package com.example.courses.di

import com.example.courses.presentation.list.presentation.CoursesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coursesModule = module {
    viewModel { CoursesViewModel(get(), get()) }
}