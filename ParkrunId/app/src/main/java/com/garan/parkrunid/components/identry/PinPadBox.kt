package com.garan.parkrunid.components.identry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

val pinPad = listOf(
    listOf(1, 2, 3),
    listOf(4, 5, 6),
    listOf(7, 8, 9),
    listOf(0)
)

@Composable
fun PinpadBox(onNumberClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        pinPad.forEach { pinRow ->
            MultiNumberRow(
                numRows = pinPad.size,
                pins = pinRow,
                onNumberClick = onNumberClick
            )
        }
    }
}