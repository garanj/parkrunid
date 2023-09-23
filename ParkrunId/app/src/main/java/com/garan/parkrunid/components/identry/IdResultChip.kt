package com.garan.parkrunid.components.identry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun IdResultChip(
    idText: String,
    isEdited: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = SolidColor(Color.DarkGray),
                shape = CircleShape
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = idText,
            modifier = Modifier.padding(vertical = 2.dp),
            color = if (isEdited) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
        )
    }
}
