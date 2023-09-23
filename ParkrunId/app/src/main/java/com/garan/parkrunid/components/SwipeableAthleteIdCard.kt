package com.garan.parkrunid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.RevealValue
import androidx.wear.compose.foundation.SwipeToReveal
import androidx.wear.compose.foundation.createAnchors
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.Icon
import com.garan.parkrunid.Athlete
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SwipeableAthleteIdCard(
    athlete: Athlete,
    onIdEntryClick: () -> Unit,
    onIdViewClick: () -> Unit,
    onResetClick: () -> Unit
) {
    val revealState = rememberRevealState(
        anchors = createAnchors(
            coveredAnchor = 0f,
            revealingAnchor = 0f,
            revealedAnchor = 0.25f
        )
    )

    val scope = rememberCoroutineScope()
    SwipeToReveal(
        state = revealState,
        action = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onResetClick() },
                contentAlignment = Alignment.Center
            ) {
                if (revealState.currentValue != RevealValue.Covered) {
                    LaunchedEffect(Unit) {
                        scope.launch {
                            delay(3000)
                            revealState.animateTo(RevealValue.Covered)
                        }
                    }
                }
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    ) {
        AthleteIdCard(
            athlete = athlete,
            onClick = {
                if (athlete == Athlete.NONE) {
                    onIdEntryClick()
                } else {
                    onIdViewClick()
                }
            }
        )
    }
}