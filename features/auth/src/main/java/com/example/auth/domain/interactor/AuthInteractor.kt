package com.example.auth.domain.interactor

interface AuthInteractor {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String, name: String): Boolean
    fun isUserLoggedIn(): Boolean
    fun logout()
}