package com.garan.parkrunid.components.identry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.parkrunid.R
import com.garan.parkrunid.theme.ParkrunIdTheme
import kotlin.math.sqrt

@Composable
fun IdEntry(
    idText: String,
    isBackspaceEnabled: Boolean,
    isConfirmEnabled: Boolean,
    onNumberClick: (Int) -> Unit,
    onBackspace: () -> Unit,
    onConfirm: () -> Unit,
    isEdited: Boolean
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    enabled = isBackspaceEnabled,
                    onClickLabel = stringResource(id = R.string.id_entry_label_backspace),
                    role = Role.Button
                ) {
                    onBackspace()
                }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Backspace,
                contentDescription = stringResource(id = R.string.id_entry_label_backspace),
                tint = if (isBackspaceEnabled) MaterialTheme.colors.primaryVariant else Color.DarkGray
            )
        }
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .weight(1 - 1 / sqrt(2.0f))
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                IdResultChip(
                    idText = idText,
                    isEdited = isEdited
                )
            }
            Row(
                modifier = Modifier
                    .weight(1 / sqrt(2.0f)),
                horizontalArrangement = Arrangement.Center
            ) {
                PinpadBox(
                    onNumberClick = onNumberClick
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable(
                    enabled = isConfirmEnabled,
                    onClickLabel = stringResource(id = R.string.id_entry_label_confirm),
                    role = Role.Button
                ) {
                    onConfirm()
                }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(id = R.string.id_entry_label_confirm),
                tint = if (isConfirmEnabled) MaterialTheme.colors.primaryVariant else Color.DarkGray
            )
        }
    }
}

@WearPreviewDevices
@Composable
fun IdEntryPreview() {
    ParkrunIdTheme {
        IdEntry(
            idText = "A123456",
            isBackspaceEnabled = true,
            isConfirmEnabled = true,
            onNumberClick = {},
            onBackspace = { },
            onConfirm = { },
            isEdited = true
        )
    }
}