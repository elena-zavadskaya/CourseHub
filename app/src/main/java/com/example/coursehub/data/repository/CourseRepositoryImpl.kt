package com.example.coursehub.data.repository

import com.example.coursehub.data.model.ApiCourse
import com.example.core.data.repository.CourseRepository
import com.example.core.data.repository.FavoriteRepository
import com.example.core.domain.model.Course
import com.example.coursehub.data.api.CoursesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

class CourseRepositoryImpl(
    private val apiService: CoursesApiService,
    private val favoriteRepository: FavoriteRepository
) : CourseRepository {

    private var allCourses: List<ApiCourse> = emptyList()

    override fun getCourses(): Flow<List<Course>> {
        return favoriteRepository.getFavoriteCourses().map { favoriteIds ->
            if (allCourses.isEmpty()) {
                allCourses = apiService.getCourses().courses
            }
            allCourses.map { apiCourse ->
                apiCourse.toDomainModel(favoriteIds.contains(apiCourse.id.toString()))
            }
        }
    }

    override suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        favoriteRepository.toggleFavorite(courseId, isFavorite)
    }

    override fun searchCourses(query: String): Flow<List<Course>> {
        return favoriteRepository.getFavoriteCourses().map { favoriteIds ->
            if (allCourses.isEmpty()) {
                allCourses = apiService.getCourses().courses
            }

            val filteredCourses = if (query.isBlank()) {
                allCourses
            } else {
                allCourses.filter { course ->
                    course.title.contains(query, ignoreCase = true) ||
                            course.text.contains(query, ignoreCase = true)
                }
            }

            filteredCourses.map { apiCourse ->
                apiCourse.toDomainModel(favoriteIds.contains(apiCourse.id.toString()))
            }
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return favoriteRepository.getFavoriteCourses().map { favoriteIds ->
            if (allCourses.isEmpty()) {
                allCourses = apiService.getCourses().courses
            }
            allCourses
                .filter { favoriteIds.contains(it.id.toString()) }
                .map { apiCourse ->
                    apiCourse.toDomainModel(true)
                }
        }.distinctUntilChanged()
    }

    private fun ApiCourse.toDomainModel(isFavorite: Boolean): Course {
        return Course(
            id = id.toString(),
            title = title,
            description = text,
            imageUrl = "",
            price = parsePrice(price),
            currency = "₽",
            date = formatDate(startDate),
            rating = rate.toFloat(),
            isFavorite = isFavorite,
            publishDate = publishDate
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