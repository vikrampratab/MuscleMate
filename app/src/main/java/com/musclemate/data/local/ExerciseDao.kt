package com.musclemate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY muscleGroup, name")
    fun observeAll(): Flow<List<ExerciseEntity>>
    @Query("SELECT * FROM exercises WHERE isFavorite = 1 ORDER BY name")
    fun observeFavorites(): Flow<List<ExerciseEntity>>
    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun count(): Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)
    @Query("UPDATE exercises SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: String)
}