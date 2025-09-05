package com.example.core.data.repository

import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourses(): Result<List<Course>>
    suspend fun toggleFavorite(courseId: String)
    suspend fun searchCourses(query: String): Result<List<Course>>
    fun getFavoriteCourses(): Flow<List<Course>>
}