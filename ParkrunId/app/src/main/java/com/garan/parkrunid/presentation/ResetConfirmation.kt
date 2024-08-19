package com.garan.parkrunid.presentation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.garan.parkrunid.R
import com.garan.parkrunid.TAG
import com.garan.parkrunid.theme.ParkrunIdTheme
import com.google.android.horologist.compose.snackbar.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ResetConfirmationRoute(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    val viewModel = hiltViewModel<ResetConfirmationViewModel>()
    val resetMessage = stringResource(id = R.string.reset_confirmation_toast_message)
    ResetConfirmation(
        onConfirmClick = {
            scope.launch {
                viewModel.resetAthleteId()
                navHostController.popBackStack()
                snackbarHostState.showSnackbar(
                    message = resetMessage,
                    actionLabel = null
                )
            }
        },
        onCancelClick = {
            navHostController.popBackStack()
        }
    )
}

@Composable
fun ResetConfirmation(
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Alert(
        title = {
            Text(stringResource(id = R.string.reset_confirmation_title))
        },
        positiveButton = {
            Button(onClick = onConfirmClick) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.reset_confirmation_confirm)
                )
            }
        },
        negativeButton = {
            Button(onClick = onCancelClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.reset_confirmation_cancel)
                )
            }
        }
    )
}

@WearPreviewDevices
@Composable
fun DeleteConfirmationPreview() {
    ParkrunIdTheme {
        ResetConfirmation(
            onCancelClick = {},
            onConfirmClick = {}
        )
    }
}