// core/src/main/java/com/example/core/domain/interactor/GetCoursesInteractor.kt
package com.example.core.domain.interactor

import com.example.core.data.repository.CourseRepository
import com.example.core.domain.model.Course

class GetCoursesInteractor(private val repository: CourseRepository) {
    suspend operator fun invoke(): Result<List<Course>> {
        return repository.getCourses()
    }
}