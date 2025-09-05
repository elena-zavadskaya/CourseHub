// core/src/main/java/com/example/core/data/model/ApiCoursesResponse.kt
package com.example.coursehub.data.model

import com.google.gson.annotations.SerializedName

data class ApiCoursesResponse(
    @SerializedName("courses")
    val courses: List<ApiCourse>
)

data class ApiCourse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("rate")
    val rate: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("hasLike")
    val hasLike: Boolean,

    @SerializedName("publishDate")
    val publishDate: String
)