package com.garan.parkrunid.presentation


import androidx.lifecycle.ViewModel
import com.garan.parkrunid.AthleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetConfirmationViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository
) : ViewModel() {

    suspend fun resetAthleteId() {
        athleteRepository.resetAthlete()
    }
}