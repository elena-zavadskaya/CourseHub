package com.example.core.domain.interactor

import com.example.core.data.repository.CourseRepository
import com.example.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

class GetCoursesInteractor(private val repository: CourseRepository) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getCourses()
    }
}