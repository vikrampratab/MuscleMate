package com.musclemate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val muscleGroup: String,
    val targetMuscle: String,
    val secondaryMuscles: String,
    val equipment: String,
    val difficulty: String,
    val animationUrl: String,
    val videoUrl: String,
    val instructions: String,
    val commonMistakes: String,
    val sets: Int,
    val reps: String,
    val restTime: Int,
    val isFavorite: Boolean = false
)