package com.example.auth.di

import com.example.auth.data.repository.AuthRepositoryImpl
import com.example.auth.domain.interactor.AuthInteractor
import com.example.auth.domain.interactor.AuthInteractorImpl
import com.example.auth.domain.repository.AuthRepository
import com.example.auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<AuthInteractor> { AuthInteractorImpl(get()) }
    viewModel { LoginViewModel(get()) }
}