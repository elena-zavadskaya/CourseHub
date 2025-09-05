// core/src/main/java/com/example/core/data/repository/CourseRepositoryImpl.kt
package com.example.core.data.repository

import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CourseRepositoryImpl : CourseRepository {

    // Временные фиктивные данные для демонстрации
    private val fakeCourses = listOf(
        Course(
            id = "1",
            title = "Java-разработчик с нуля",
            description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API.",
            imageUrl = "",
            price = 999,
            currency = "₽",
            date = "22 Мая 2024",
            rating = 4.9f,
            isFavorite = false
        ),
        Course(
            id = "2",
            title = "Android-разработчик",
            description = "Научитесь создавать мобильные приложения для Android с нуля.",
            imageUrl = "",
            price = 1299,
            currency = "₽",
            date = "15 Июня 2024",
            rating = 4.7f,
            isFavorite = true
        )
    )

    private val favoriteCourses = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): Result<List<Course>> {
        return Result.success(fakeCourses)
    }

    override suspend fun toggleFavorite(courseId: String) {
        // Логика переключения избранного
        val updatedCourses = fakeCourses.map { course ->
            if (course.id == courseId) {
                course.copy(isFavorite = !course.isFavorite)
            } else {
                course
            }
        }

        // Обновляем список избранных
        favoriteCourses.value = updatedCourses.filter { it.isFavorite }
    }

    override suspend fun searchCourses(query: String): Result<List<Course>> {
        val filteredCourses = fakeCourses.filter { course ->
            course.title.contains(query, ignoreCase = true) ||
                    course.description.contains(query, ignoreCase = true)
        }
        return Result.success(filteredCourses)
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return favoriteCourses
    }
}