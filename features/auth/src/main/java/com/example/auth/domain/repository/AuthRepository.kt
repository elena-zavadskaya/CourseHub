package com.example.auth.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String, name: String): Boolean
    fun isUserLoggedIn(): Boolean
    fun logout()
}