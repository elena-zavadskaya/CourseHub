package com.example.auth.data.repository

import com.example.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    // Тестовый пользователь
    private val testUser = TestUser(
        email = "test@example.com",
        password = "password123",
        name = "Тестовый Пользователь"
    )

    // Токен/флаг аутентификации (в реальном приложении хранился бы в Secure SharedPrefs или Encrypted Storage)
    private var authToken: String? = null

    override suspend fun login(email: String, password: String): Boolean {
        // Имитация сетевой задержки
        delay(1000)

        return if (email == testUser.email && password == testUser.password) {
            authToken = "mock_jwt_token_${System.currentTimeMillis()}"
            true
        } else {
            false
        }
    }

    override suspend fun register(email: String, password: String, name: String): Boolean {
        // В реальном приложении здесь была бы регистрация через API
        delay(1000)
        return false // Пока не реализовано
    }

    override fun isUserLoggedIn(): Boolean {
        return authToken != null
    }

    override fun logout() {
        authToken = null
    }

    // Модель тестового пользователя
    private data class TestUser(
        val email: String,
        val password: String,
        val name: String
    )
}