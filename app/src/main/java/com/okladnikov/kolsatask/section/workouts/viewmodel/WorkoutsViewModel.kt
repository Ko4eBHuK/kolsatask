package com.okladnikov.kolsatask.section.workouts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okladnikov.kolsatask.section.workouts.data.WorkoutsRepository
import com.okladnikov.kolsatask.utils.Status.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository
) : ViewModel() {
    private val _screenState = MutableStateFlow(WorkoutsScreenState())
    val screenState: StateFlow<WorkoutsScreenState>
        get() = _screenState

    init {
        fetchWorkouts()
    }

    fun processIntent(intent: WorkoutsScreenIntent) {
        when (intent) {
            WorkoutsScreenIntent.Refresh -> fetchWorkouts()
            WorkoutsScreenIntent.CloseError -> {
                _screenState.value = _screenState.value.copy(isError = false)
            }
        }
    }

    private fun fetchWorkouts() = viewModelScope.launch(Dispatchers.IO) {
        workoutsRepository.getWorkouts().collect { workoutsCallState ->
            when (workoutsCallState.status) {
                LOADING -> {
                    _screenState.value = _screenState.value.copy(
                        isLoading = true,
                        isError = false,
                        message = workoutsCallState.message ?: "Загрузка"
                    )
                }
                ERROR -> {
                    _screenState.value = _screenState.value.copy(
                        isLoading = false,
                        isError = true,
                        message = workoutsCallState.message ?: "Ошибка"
                    )
                }
                SUCCESS -> {
                    _screenState.value = _screenState.value.copy(
                        isLoading = false,
                        workouts = workoutsCallState.data ?: listOf()
                    )
                }
            }
        }
    }
}
