package com.example.core.data.repository

import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourses(): Flow<List<Course>>
    suspend fun toggleFavorite(courseId: String, isFavorite: Boolean)
    fun searchCourses(query: String): Flow<List<Course>>
    fun getFavoriteCourses(): Flow<List<Course>>
}