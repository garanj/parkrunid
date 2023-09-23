package com.garan.parkrunid.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun IdViewRoute() {
    val viewModel = hiltViewModel<IdViewScreenViewModel>()
    val uiState by viewModel.uiState

    IdViewScreen(uiState = uiState)
}

@Composable
fun IdViewScreen(uiState: IdViewScreenViewModel.UiState) {
    var toggle by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.707f)
                .clickable {
                    toggle = (toggle + 1) % 2
                },
            contentAlignment = Alignment.Center
        ) {
            if (uiState is IdViewScreenViewModel.UiState.Ready) {
                if (toggle == 0) {
                    Image(
                        bitmap = uiState.qrCode.asImageBitmap(),
                        contentScale = ContentScale.Inside,
                        contentDescription = "some useful description",
                    )
                } else {
                    Row {
                        Image(
                            bitmap = uiState.barcode.asImageBitmap(),
                            contentScale = ContentScale.Inside,
                            contentDescription = "some useful description",
                        )
                    }
                    Row {

                    }
                }

            }
        }
    }
}