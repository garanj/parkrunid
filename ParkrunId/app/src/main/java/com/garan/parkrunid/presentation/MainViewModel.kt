package com.garan.parkrunid.presentation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.AthleteRepository
import com.garan.parkrunid.TileInstalledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository,
    private val tileInstalledUseCase: TileInstalledUseCase
) : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    val tileInstalled = tileInstalledUseCase.isTileInstalled

    init {
        viewModelScope.launch {
            athleteRepository.athlete.collect {
                uiState.value = UiState.Ready(it)
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Ready(
            val athlete: Athlete
        ) : UiState()
    }
}