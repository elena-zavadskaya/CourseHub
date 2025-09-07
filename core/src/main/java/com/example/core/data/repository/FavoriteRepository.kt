// core/src/main/java/com/example/core/data/repository/FavoriteRepository.kt
package com.example.core.data.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun toggleFavorite(courseId: String, isFavorite: Boolean)
    fun getFavoriteCourses(): Flow<Set<String>>
}