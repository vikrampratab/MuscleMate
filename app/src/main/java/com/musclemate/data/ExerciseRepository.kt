package com.musclemate.data

import com.musclemate.data.local.ExerciseDao
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val dao: ExerciseDao) {
    val exercises: Flow<List<com.musclemate.data.local.ExerciseEntity>> = dao.observeAll()
    val favorites: Flow<List<com.musclemate.data.local.ExerciseEntity>> = dao.observeFavorites()
    suspend fun seedIfNeeded() { if (dao.count() == 0) dao.insertAll(SeedData.exercises) }
    suspend fun toggleFavorite(id: String) { dao.toggleFavorite(id) }
}