package com.garan.parkrunid

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val IS_INSTALLED_KEY = booleanPreferencesKey("is_tile_installed")

class TileInstalledUseCase @Inject constructor(
    @ApplicationContext val appContext: Context
) {
    val isTileInstalled = appContext.dataStore.data.map { prefs ->
        prefs[IS_INSTALLED_KEY] ?: false
    }
}