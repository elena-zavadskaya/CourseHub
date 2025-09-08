package com.example.core.domain.interactor

import com.example.core.data.repository.CourseRepository
import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

class CoursesInteractor (
    private val repository: CourseRepository
) {
    fun getCourses(): Flow<List<Course>> = repository.getCourses()

    suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        repository.toggleFavorite(courseId, isFavorite)
    }

    fun searchCourses(query: String): Flow<List<Course>> = repository.searchCourses(query)

    fun getFavoriteCourses(): Flow<List<Course>> = repository.getFavoriteCourses()
}