// app/src/main/java/com/example/coursehub/data/CourseRepositoryImpl.kt
package com.example.coursehub.data

import com.example.coursehub.data.model.ApiCourse
import com.example.core.data.repository.CourseRepository
import com.example.core.domain.model.Course
import com.example.coursehub.data.api.CoursesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Locale

class CourseRepositoryImpl(
    private val apiService: CoursesApiService
) : CourseRepository {

    private val favoriteCourses = MutableStateFlow<List<Course>>(emptyList())

    override suspend fun getCourses(): Result<List<Course>> {
        return try {
            val apiResponse = apiService.getCourses()
            val courses = apiResponse.courses.map { apiCourse ->
                apiCourse.toDomainModel()
            }
            Result.success(courses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(courseId: String) {
        // Логика переключения избранного
        // Пока оставим заглушку, так как API не поддерживает это
    }

    override suspend fun searchCourses(query: String): Result<List<Course>> {
        // Логика поиска
        // Пока оставим заглушку
        return Result.success(emptyList())
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return favoriteCourses
    }

    private fun ApiCourse.toDomainModel(): Course {
        return Course(
            id = id.toString(),
            title = title,
            description = text,
            imageUrl = "", // Заглушка, так как в API нет изображений
            price = parsePrice(price),
            currency = "₽",
            date = formatDate(startDate),
            rating = rate.toFloat(),
            isFavorite = hasLike
        )
    }

    private fun parsePrice(priceString: String): Int {
        return try {
            priceString.replace(" ", "").toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun formatDate(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            inputDate
        }
    }
}