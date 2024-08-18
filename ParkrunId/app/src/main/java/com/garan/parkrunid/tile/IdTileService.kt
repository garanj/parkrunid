package com.garan.parkrunid.tile

import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.wear.protolayout.ActionBuilders.LoadAction
import androidx.wear.protolayout.DeviceParametersBuilders.DeviceParameters
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Image
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.ResourceBuilders.ImageResource
import androidx.wear.protolayout.ResourceBuilders.InlineImageResource
import androidx.wear.protolayout.ResourceBuilders.Resources
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Chip
import androidx.wear.protolayout.material.ChipColors
import androidx.wear.tiles.EventBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.garan.parkrunid.Athlete
import com.garan.parkrunid.AthleteRepository
import com.garan.parkrunid.IS_INSTALLED_KEY
import com.garan.parkrunid.R
import com.garan.parkrunid.dataStore
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer

const val TAG = "PLOP"

@OptIn(ExperimentalHorologistApi::class)
class IdTileService() : SuspendingTileService() {
    private val athleteRepository by lazy { AthleteRepository(this) }

    private val FLIP_CLICK_ID = "should_flip"
    private val SETUP_CLICK_ID = "setup_click"

    private val ID_CODE_TYPE_KEY = intPreferencesKey("id_code_type_key")

    private val ID_CODE_QR_CODE = 0
    private val ID_CODE_BAR_CODE = 1

    private val ID_CODE_IMAGE_RESOURCE_ID = "id_code"

    private val SETUP_TILE_RESOURCE_ID = "setup"

    private val BUTTON_BG_COLOR = "#4CAF50"
    private val BUTTON_FG_COLOR = "#000000"

    private lateinit var idCodeByteBuffer: ByteBuffer
    private lateinit var idCodeBitmap: Bitmap
    private var resourcesVersion = "1"

    override fun onTileAddEvent(requestParams: EventBuilders.TileAddEvent) {
        super.onTileAddEvent(requestParams)
        runBlocking {
            dataStore.edit { prefs ->
                prefs[IS_INSTALLED_KEY] = true
            }
        }
    }

    override fun onTileRemoveEvent(requestParams: EventBuilders.TileRemoveEvent) {
        super.onTileRemoveEvent(requestParams)
        runBlocking {
            dataStore.edit { prefs ->
                prefs[IS_INSTALLED_KEY] = false
            }
        }
    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        if (requestParams.currentState.lastClickableId == SETUP_CLICK_ID) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("parkrunid://setup"))

            TaskStackBuilder.create(this)
                .addNextIntent(intent)
                .startActivities()
        }

        val athlete = athleteRepository.athlete.first()
        return if (athlete == Athlete.NONE) {
            setupTile(requestParams.deviceConfiguration)
        } else {
            idCodeTile(requestParams)
        }
    }

    private fun setupTile(deviceParameters: DeviceParameters): TileBuilders.Tile {
        resourcesVersion = SETUP_TILE_RESOURCE_ID
        return TileBuilders.Tile.Builder()
            .setResourcesVersion(SETUP_TILE_RESOURCE_ID)
            .setTileTimeline(
                TimelineBuilders.Timeline
                    .fromLayoutElement(
                        LayoutElementBuilders.Box.Builder()
                            .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
                            .setHeight(DimensionBuilders.expand())
                            .setWidth(DimensionBuilders.expand())
                            .addContent(
                                Chip.Builder(this, createSetupClickable(), deviceParameters)
                                    .setPrimaryLabelContent(getString(R.string.tile_setup_label))
                                    .setChipColors(
                                        ChipColors(
                                            Color.parseColor(BUTTON_BG_COLOR),
                                            Color.parseColor(BUTTON_FG_COLOR)
                                        )
                                    )
                                    .build()

                            )
                            .build()
                    )
            )
            .build()
    }

    private suspend fun idCodeTile(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val qrCodeWidth = requestParams.deviceConfiguration.screenWidthDp * 0.707f
        val shouldFlip = requestParams.currentState.lastClickableId == FLIP_CLICK_ID

        val imageRepresentation = loadImageRepresentationSetting(shouldFlip)
        loadImages(imageRepresentation)

        return TileBuilders.Tile.Builder()
            .setResourcesVersion(resourcesVersion)
            .setTileTimeline(
                TimelineBuilders.Timeline
                    .fromLayoutElement(
                        LayoutElementBuilders.Box.Builder()
                            .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
                            .setHeight(DimensionBuilders.expand())
                            .setWidth(DimensionBuilders.expand())
                            .addContent(
                                Image.Builder()
                                    .setModifiers(createReloadModifier())
                                    .setResourceId(ID_CODE_IMAGE_RESOURCE_ID)
                                    .setHeight(DimensionBuilders.dp(qrCodeWidth))
                                    .setWidth(DimensionBuilders.dp(qrCodeWidth))
                                    .build()
                            )
                            .build()
                    )
            )
            .build()
    }

    override suspend fun resourcesRequest(requestParams: RequestBuilders.ResourcesRequest): Resources {
        val imageRepresentation = loadImageRepresentationSetting(false)
        loadImages(imageRepresentation)

        val resources = Resources.Builder()
            .apply {
                if (resourcesVersion != SETUP_TILE_RESOURCE_ID) {
                    addIdToImageMapping(
                        ID_CODE_IMAGE_RESOURCE_ID,
                        ImageResource.Builder()
                            .setInlineResource(
                                InlineImageResource.Builder()
                                    .setData(idCodeByteBuffer.array())
                                    .setFormat(ResourceBuilders.IMAGE_FORMAT_ARGB_8888)
                                    .setHeightPx(idCodeBitmap.height)
                                    .setWidthPx(idCodeBitmap.width)
                                    .build()
                            ).build()
                    )
                }
            }
            .setVersion(resourcesVersion)
            .build()
        return resources
    }

    private suspend fun loadImages(imageRepresentation: Int) {
        idCodeBitmap = if (imageRepresentation == ID_CODE_QR_CODE) {
            athleteRepository.loadQrCodeBitmap()
        } else {
            athleteRepository.loadBarcodeBitmap()
        }
        idCodeByteBuffer = ByteBuffer.allocate(idCodeBitmap.byteCount)
        idCodeBitmap.copyPixelsToBuffer(idCodeByteBuffer)
        val id = athleteRepository.athlete.first().id
        resourcesVersion = "${id}_$imageRepresentation"
    }

    private suspend fun loadImageRepresentationSetting(shouldFlip: Boolean): Int {
        var imageRepresentation = dataStore.data.map { prefs ->
            prefs[ID_CODE_TYPE_KEY] ?: ID_CODE_BAR_CODE
        }.first()

        if (shouldFlip) {
            imageRepresentation = (imageRepresentation + 1) % 2
            dataStore.edit { prefs ->
                prefs[ID_CODE_TYPE_KEY] = imageRepresentation
            }
        }
        return imageRepresentation
    }

    private fun createSetupClickable() = Clickable.Builder()
        .setId(SETUP_CLICK_ID)
        .setOnClick(
            LoadAction.Builder().build()
        )
        .build()

    private fun createReloadModifier() = ModifiersBuilders.Modifiers.Builder()
        .setClickable(
            Clickable.Builder()
                .setId(FLIP_CLICK_ID)
                .setOnClick(
                    LoadAction.Builder().build()
                )
                .build()
        )
        .build()
}