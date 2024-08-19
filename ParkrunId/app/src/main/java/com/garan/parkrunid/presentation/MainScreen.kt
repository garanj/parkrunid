package com.garan.parkrunid.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.components.AthleteIdCard
import com.garan.parkrunid.components.SwipeableAthleteIdCard


@Composable
fun MainScreenRoute(
    onIdEntryClick: () -> Unit,
    onIdViewClick: () -> Unit,
    onResetClick: () -> Unit,
    onTileInfoButtonClick: () -> Unit
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val uiState by viewModel.uiState
    if (uiState is MainViewModel.UiState.Ready) {
        val readyState = uiState as MainViewModel.UiState.Ready
        val isTileInstalled by viewModel.tileInstalled.collectAsStateWithLifecycle(true)
        MainScreen(
            athlete = readyState.athlete,
            onIdEntryClick = onIdEntryClick,
            onIdViewClick = onIdViewClick,
            onResetClick = onResetClick,
            onTileInfoButtonClick = onTileInfoButtonClick,
            isTileInstalled = isTileInstalled
        )
    }
}

@Composable
fun MainScreen(
    athlete: Athlete,
    onIdEntryClick: () -> Unit,
    onIdViewClick: () -> Unit,
    onResetClick: () -> Unit,
    onTileInfoButtonClick: () -> Unit,
    isTileInstalled: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(1 - 2 * 0.052f),
            horizontalArrangement = Arrangement.Center
        ) {
            if (athlete != Athlete.NONE) {
                SwipeableAthleteIdCard(
                    athlete = athlete,
                    onIdEntryClick = onIdEntryClick,
                    onIdViewClick = onIdViewClick,
                    onResetClick = onResetClick
                )
            } else {
                AthleteIdCard(
                    athlete = Athlete.NONE,
                    onClick = onIdEntryClick
                )
            }
        }
    }
    if (!isTileInstalled) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            CompactButton(onClick = onTileInfoButtonClick) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = "How to add the Tile"
                )
            }
        }
    }
}