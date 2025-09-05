// courses/src/main/java/com/example/courses/presentation/list/presentation/CoursesViewModel.kt
package com.example.courses.presentation.list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.interactor.GetCoursesInteractor
import com.example.core.domain.model.Course
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val getCoursesInteractor: GetCoursesInteractor
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun loadCourses() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = getCoursesInteractor()
                if (result.isSuccess) {
                    _courses.value = result.getOrNull() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = result.exceptionOrNull()?.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(courseId: String) {
        // Пока оставим заглушку, так как API не поддерживает это
        viewModelScope.launch {
            // Здесь будет логика переключения избранного
        }
    }

    fun searchCourses(query: String) {
        // Пока оставим заглушку
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Здесь будет логика поиска
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun openFilters() {
        // Реализация открытия фильтров
    }

    fun openSortOptions() {
        // Реализация открытия опций сортировки
    }
}