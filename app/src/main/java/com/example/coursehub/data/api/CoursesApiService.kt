package com.example.coursehub.data.api

import com.example.coursehub.data.model.ApiCoursesResponse
import retrofit2.http.GET

interface CoursesApiService {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): ApiCoursesResponse
}