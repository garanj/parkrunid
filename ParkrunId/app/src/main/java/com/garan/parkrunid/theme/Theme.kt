package com.garan.parkrunid.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

internal val wearColorPalette: Colors = Colors(
    primary = Color(0x4C, 0xAF, 0x50, 0xFF),
    primaryVariant = Color.LightGray,
    error = Color.Red,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)

@Composable
fun ParkrunIdTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = wearColorPalette,
        content = content
    )
}
