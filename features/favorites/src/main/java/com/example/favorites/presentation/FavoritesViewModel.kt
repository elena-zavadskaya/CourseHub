package com.example.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.CourseRepository
import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoritesViewModel : ViewModel(), KoinComponent {
    private val courseRepository: CourseRepository by inject()

    private val _favorites = MutableStateFlow<List<Course>>(emptyList())
    val favorites: StateFlow<List<Course>> get() = _favorites

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                courseRepository.getFavoriteCourses().collect { courses ->
                    _favorites.value = courses
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                courseRepository.toggleFavorite(courseId, isFavorite)
                // Обновляем локальное состояние
                if (!isFavorite) {
                    _favorites.value = _favorites.value.filter { it.id != courseId }
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}