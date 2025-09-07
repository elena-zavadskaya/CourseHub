// core/src/main/java/com/example/core/domain/model/Course.kt
package com.example.core.domain.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val currency: String,
    val date: String,
    val rating: Float,
    val isFavorite: Boolean = false,
    val publishDate: String // Добавляем поле для даты публикации
)