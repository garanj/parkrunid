package com.garan.parkrunid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.navDeepLink
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.garan.parkrunid.presentation.IdEntryRoute
import com.garan.parkrunid.presentation.IdViewRoute
import com.garan.parkrunid.presentation.MainScreenRoute
import com.garan.parkrunid.presentation.ResetConfirmationRoute
import com.garan.parkrunid.presentation.TileEducationScreen
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.snackbar.SnackbarHostState

@Composable
fun ParkrunIdNavigation() {
    val navController = rememberSwipeDismissableNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    AppScaffold {
        SwipeDismissableNavHost(
            startDestination = Screens.MAIN.route,
            navController = navController
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
            composable(
                route = Screens.TILE_EDUCATION.route
            ) {
                TileEducationScreen()
            }
        }
        ParkrunIdSnackbar(snackbarHostState)
    }
}