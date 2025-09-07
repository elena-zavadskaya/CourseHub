// courses/src/main/java/com/example/courses/presentation/list/presentation/CoursesViewModel.kt
package com.example.courses.presentation.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.CourseRepository
import com.example.core.domain.interactor.GetCoursesInteractor
import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class CoursesViewModel(
    private val getCoursesInteractor: GetCoursesInteractor,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> get() = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Флаг для отслеживания текущего состояния сортировки
    private var isSortedByDate = false

    init {
        loadCourses()
    }

    fun loadCourses() {
        _isLoading.value = true
        viewModelScope.launch {
            getCoursesInteractor()
                .catch { e ->
                    _error.value = e.message
                    _isLoading.value = false
                }
                .collect { courses ->
                    _courses.value = courses
                    _isLoading.value = false
                }
        }
    }

    fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                courseRepository.toggleFavorite(courseId, isFavorite)
                // Обновляем локальное состояние
                _courses.value = _courses.value.map { course ->
                    if (course.id == courseId) course.copy(isFavorite = isFavorite) else course
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchCourses(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            courseRepository.searchCourses(query)
                .catch { e ->
                    _error.value = e.message
                    _isLoading.value = false
                }
                .collect { courses ->
                    _courses.value = courses
                    _isLoading.value = false
                }
        }
    }

    fun openFilters() {
        // Реализация открытия фильтров
    }

    fun openSortOptions() {
        // Реализация сортировки по дате публикации
        sortCoursesByPublishDate()
    }

    private fun sortCoursesByPublishDate() {
        val currentCourses = _courses.value.toMutableList()

        if (isSortedByDate) {
            // Если уже отсортировано, возвращаем к исходному порядку
            loadCourses()
        } else {
            // Сортируем по дате публикации в порядке убывания
            currentCourses.sortByDescending { course ->
                try {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(course.publishDate)
                } catch (e: Exception) {
                    null
                }
            }
            _courses.value = currentCourses
        }

        isSortedByDate = !isSortedByDate
    }
}