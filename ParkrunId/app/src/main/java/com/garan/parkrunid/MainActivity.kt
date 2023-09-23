package com.garan.parkrunid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.garan.parkrunid.navigation.ParkrunIdNavigation
import com.garan.parkrunid.theme.ParkrunIdTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ParkrunIdTheme {
                ParkrunIdNavigation()
            }
        }
    }
}