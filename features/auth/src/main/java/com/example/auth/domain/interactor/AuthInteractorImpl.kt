package com.example.auth.domain.interactor

import com.example.auth.domain.repository.AuthRepository

class AuthInteractorImpl(
    private val authRepository: AuthRepository
) : AuthInteractor {

    override suspend fun login(email: String, password: String): Boolean {
        return authRepository.login(email, password)
    }

    override suspend fun register(email: String, password: String, name: String): Boolean {
        return authRepository.register(email, password, name)
    }

    override fun isUserLoggedIn(): Boolean {
        return authRepository.isUserLoggedIn()
    }

    override fun logout() {
        authRepository.logout()
    }
}