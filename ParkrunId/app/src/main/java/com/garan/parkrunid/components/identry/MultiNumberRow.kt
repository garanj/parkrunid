package com.garan.parkrunid.components.identry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ColumnScope.MultiNumberRow(
    numRows: Int,
    pins: List<Int>,
    onNumberClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .weight(1f / numRows)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pins.forEach { pinValue ->
            NumericButton(
                num = pinValue,
                onNumberClick = { onNumberClick(pinValue) }
            )
        }
    }
}