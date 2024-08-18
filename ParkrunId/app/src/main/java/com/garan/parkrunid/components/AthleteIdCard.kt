package com.garan.parkrunid.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.CardDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TitleCard
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.R
import com.garan.parkrunid.theme.ParkrunIdTheme

@Composable
fun AthleteIdCard(
    athlete: Athlete,
    onClick: () -> Unit
) {
    TitleCard(
        onClick = onClick,
        title = { },
        backgroundPainter = CardDefaults.imageWithScrimBackgroundPainter(
            backgroundImagePainter = painterResource(id = R.drawable.barcode),
            backgroundImageScrimBrush = Brush.linearGradient(
                start = Offset(100f, 0f),
                colors =
                listOf(
                    MaterialTheme.colors.surface.copy(alpha = 1.0f),
                    MaterialTheme.colors.surface.copy(alpha = 0.5f)
                )
            )
        ),
        contentColor = MaterialTheme.colors.onSurface,
        titleColor = MaterialTheme.colors.onSurface
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (athlete == Athlete.NONE) {
                Text(
                    text = stringResource(id = R.string.start_menu_set_id),
                    style = MaterialTheme.typography.title2
                )
            } else {
                Text(
                    text = athlete.id,
                    style = MaterialTheme.typography.title1
                )
            }
        }
    }
}

@WearPreviewDevices
@Composable
fun AthleteIdChipPreview() {
    ParkrunIdTheme {
        Box(
            modifier = Modifier.fillMaxSize(0.8f),
            contentAlignment = Alignment.Center
        ) {
            AthleteIdCard(
                athlete = Athlete("A123456"),
                onClick = {}
            )
        }
    }
}

@WearPreviewDevices
@Composable
fun NoAthleteIdChipPreview() {
    ParkrunIdTheme {
        Box(
            modifier = Modifier.fillMaxSize(0.8f),
            contentAlignment = Alignment.Center
        ) {
            AthleteIdCard(
                athlete = Athlete.NONE,
                onClick = {}
            )
        }
    }
}