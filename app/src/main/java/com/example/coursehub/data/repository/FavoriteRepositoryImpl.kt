package com.example.coursehub.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.core.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl constructor(
    private val dataStore: DataStore<Preferences>
) : FavoriteRepository {

    companion object {
        val FAVORITE_COURSES_KEY = stringSetPreferencesKey("favorite_courses")
    }

    override suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITE_COURSES_KEY] ?: setOf()
            val newFavorites = if (isFavorite) {
                currentFavorites + courseId
            } else {
                currentFavorites - courseId
            }
            preferences[FAVORITE_COURSES_KEY] = newFavorites
        }
    }

    override fun getFavoriteCourses(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[FAVORITE_COURSES_KEY] ?: setOf()
        }
    }
}