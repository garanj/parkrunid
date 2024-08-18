package com.garan.parkrunid.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.components.identry.IdEntry
import com.garan.parkrunid.theme.ParkrunIdTheme

@Composable
fun IdEntryRoute() {
    val viewModel = hiltViewModel<IdEntryScreenViewModel>()
    val uiState by viewModel.uiState

    when (uiState) {
        is IdEntryScreenViewModel.UiState.Loading -> {}
        is IdEntryScreenViewModel.UiState.Ready -> {
            val readyState = uiState as IdEntryScreenViewModel.UiState.Ready
            IdEntryScreen(
                readyState = readyState,
                onConfirm = { id ->
                    viewModel.setAthlete(id)
                }
            )
        }
    }
}

@Composable
fun IdEntryScreen(
    readyState: IdEntryScreenViewModel.UiState.Ready,
    onConfirm: (String) -> Unit
) {
    var id by remember { mutableStateOf(readyState.athlete.id) }
    IdEntry(
        idText = id,
        isBackspaceEnabled = id.length > 1,
        isConfirmEnabled = id.length > 1 && id != readyState.athlete.id,
        onNumberClick = { num ->
            id += num.toString()
        },
        onBackspace = {
            if (id.length > 1) {
                id = id.dropLast(1)
            }
        },
        onConfirm = {
            onConfirm(id)
        },
        isEdited = id != readyState.athlete.id
    )
}

@WearPreviewDevices
@Composable
fun IdEntryScreenPreview() {
    val readyState = IdEntryScreenViewModel.UiState.Ready(
        Athlete(id = "A123456")
    )
    ParkrunIdTheme {
        IdEntryScreen(
            readyState = readyState, onConfirm = {})
    }
}