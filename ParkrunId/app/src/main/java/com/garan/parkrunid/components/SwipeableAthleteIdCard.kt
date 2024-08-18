package com.garan.parkrunid.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.RevealValue
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealDefaults
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import com.garan.parkrunid.Athlete
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalWearFoundationApi::class, ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableAthleteIdCard(
    athlete: Athlete,
    onIdEntryClick: () -> Unit,
    onIdViewClick: () -> Unit,
    onResetClick: () -> Unit
) {
    val revealState = rememberRevealState()
    val scope = rememberCoroutineScope()

    SwipeToRevealChip(
        revealState = revealState,
        primaryAction = {
            SwipeToRevealPrimaryAction(
                revealState = revealState,
                icon = { Icon(SwipeToRevealDefaults.Delete, "Delete") },
                onClick = onResetClick,
                label = {}
            )
            if (revealState.currentValue != RevealValue.Covered) {
                LaunchedEffect(Unit) {
                    scope.launch {
                        delay(3000)
                        revealState.animateTo(RevealValue.Covered)
                    }
                }
            }
        },
        onFullSwipe = {}
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