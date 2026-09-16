package com.musclemate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musclemate.data.ExerciseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(private val repository: ExerciseRepository) : ViewModel() {
    val exercises = repository.exercises.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val favorites = repository.favorites.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    init { viewModelScope.launch { repository.seedIfNeeded() } }
    fun toggleFavorite(id: String) { viewModelScope.launch { repository.toggleFavorite(id) } }
}