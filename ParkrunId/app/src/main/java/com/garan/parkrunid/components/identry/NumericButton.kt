package com.garan.parkrunid.components.identry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun NumericButton(
    num: Int,
    onNumberClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = SolidColor(Color.Black),
                shape = CircleShape
            )
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable(
                enabled = true,
                onClickLabel = num.toString(),
                role = Role.Button,
                onClick = onNumberClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$num",
            style = MaterialTheme.typography.title1
        )
    }
}
