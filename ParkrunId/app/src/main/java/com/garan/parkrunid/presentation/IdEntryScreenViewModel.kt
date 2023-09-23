package com.garan.parkrunid.presentation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.AthleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdEntryScreenViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository
) : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)

    init {
        viewModelScope.launch {
            athleteRepository.athlete.collect {
                uiState.value = UiState.Ready(it)
            }
        }
    }

    fun setAthlete(id: String) {
        viewModelScope.launch {
            val athlete = athleteRepository.setAthlete(id)
            uiState.value = UiState.Ready(athlete)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Ready(
            val athlete: Athlete
        ) : UiState()
    }
}