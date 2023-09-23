package com.garan.parkrunid.presentation


import android.graphics.Bitmap
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
class IdViewScreenViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository
) : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)

    init {
        viewModelScope.launch {
            athleteRepository.athlete.collect {
                uiState.value = if (it == Athlete.NONE) {
                    UiState.NoAthlete
                } else {
                    val barcode = athleteRepository.loadBarcodeBitmap()
                    val qrCode = athleteRepository.loadQrCodeBitmap()
                    UiState.Ready(
                        athlete = it,
                        barcode = barcode,
                        qrCode = qrCode
                    )
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object NoAthlete : UiState()
        data class Ready(
            val athlete: Athlete,
            val barcode: Bitmap,
            val qrCode: Bitmap
        ) : UiState()
    }
}