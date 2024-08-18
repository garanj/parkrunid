package com.garan.parkrunid.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.horologist.compose.snackbar.SnackbarHost
import com.google.android.horologist.compose.snackbar.SnackbarHostState

@Composable
fun ParkrunIdSnackbar(snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.fillMaxSize()
    ) { data ->
        LaunchedEffect(data) {
            Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
        }
    }
}
