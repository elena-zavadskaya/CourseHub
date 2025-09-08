package com.example.courses.presentation.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.CoursesInteractor
import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class CoursesViewModel(
    private val coursesInteractor: CoursesInteractor
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> get() = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private var isSortedByDate = false

    init {
        loadCourses()
    }

    fun loadCourses() {
        _isLoading.value = true
        viewModelScope.launch {
            coursesInteractor.getCourses()
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
                coursesInteractor.toggleFavorite(courseId, isFavorite)
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
            coursesInteractor.searchCourses(query)
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
        sortCoursesByPublishDate()
    }

    private fun sortCoursesByPublishDate() {
        val currentCourses = _courses.value.toMutableList()

        if (isSortedByDate) {
            loadCourses()
        } else {
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