package com.garan.parkrunid.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.navDeepLink
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.garan.parkrunid.presentation.IdEntryRoute
import com.garan.parkrunid.presentation.IdViewRoute
import com.garan.parkrunid.presentation.MainScreenRoute
import com.garan.parkrunid.presentation.ResetConfirmationRoute
import com.garan.parkrunid.presentation.TileEducationScreen
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.navscaffold.WearNavScaffold
import com.google.android.horologist.compose.navscaffold.scrollable
import com.google.android.horologist.compose.snackbar.SnackbarHost
import com.google.android.horologist.compose.snackbar.SnackbarHostState

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun ParkrunIdNavigation() {
    val navController = rememberSwipeDismissableNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    WearNavScaffold(
        startDestination = Screens.MAIN.route,
        navController = navController,
        snackbar = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxSize()
            ) { data ->
                LaunchedEffect(data) {
                    Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    ) {
        composable(
            route = Screens.MAIN.route
        ) {
            MainScreenRoute(
                onIdEntryClick = {
                    navController.navigate(Screens.ID_ENTRY.route)
                },
                onIdViewClick = {
                    navController.navigate(Screens.ID_VIEW.route)
                },
                onResetClick = {
                    navController.navigate(Screens.ID_RESET.route)
                },
                onTileInfoButtonClick = {
                    navController.navigate(Screens.TILE_EDUCATION.route)
                }
            )
        }
        composable(
            route = Screens.ID_VIEW.route
        ) {
            IdViewRoute()
        }
        composable(
            route = Screens.ID_ENTRY.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "parkrunid://setup"
                }
            )

        ) {
            IdEntryRoute()
        }
        composable(
            route = Screens.ID_RESET.route
        ) {
            ResetConfirmationRoute(
                scope = scope,
                snackbarHostState = snackbarHostState,
                navHostController = navController
            )
        }
        scrollable(
            route = Screens.TILE_EDUCATION.route,
            columnStateFactory = ScalingLazyColumnDefaults.scalingLazyColumnDefaults()
        ) {
            TileEducationScreen(
                it.columnState
            )
        }
    }
}