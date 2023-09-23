package com.garan.parkrunid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.wear.tiles.TileService
import com.garan.parkrunid.tile.IdTileService
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


data class Athlete(
    val id: String = "",
) {
    companion object {
        val NONE = Athlete("A")
    }
}

class AthleteRepository @Inject constructor(
    @ApplicationContext val appContext: Context
) {
    private val barcodeFileName = "barcode.png"
    private val qrCodeFileName = "qrcode.png"

    private val width = 300
    private val qrCodeHeight = 300
    private val barcodeHeight = 150

    val athlete: Flow<Athlete> = appContext.dataStore.data.map { prefs ->
        val athleteId = prefs[ATHLETE_ID_KEY]
        if (athleteId.isNullOrEmpty() || athleteId == "A") {
            Athlete.NONE
        } else {
            Athlete(
                id = athleteId
            )
        }
    }

    suspend fun setAthlete(id: String): Athlete {
        appContext.dataStore.edit { prefs ->
            setBarcode(id)
            setQrCode(id)
            prefs[ATHLETE_ID_KEY] = id
        }
        TileService.getUpdater(appContext).requestUpdate(IdTileService::class.java)
        return Athlete(id)
    }

    suspend fun resetAthlete() {
        appContext.dataStore.edit { prefs ->
            prefs[ATHLETE_ID_KEY] = ""
        }
        appContext.deleteFile(barcodeFileName)
        appContext.deleteFile(qrCodeFileName)
        TileService.getUpdater(appContext).requestUpdate(IdTileService::class.java)
    }

    fun loadBarcodeBitmap() = loadBitmap(barcodeFileName)

    fun loadQrCodeBitmap() = loadBitmap((qrCodeFileName))

    private fun loadBitmap(filename: String): Bitmap {
        val stream = appContext.openFileInput(filename)
        val bitmap = BitmapFactory.decodeStream(stream)
        stream.close()
        return bitmap
    }

    private fun setBarcode(id: String): Bitmap {
        val writer = com.google.zxing.oned.Code128Writer()
        val matrix = writer.encode(
            id,
            BarcodeFormat.CODE_128,
            width,
            barcodeHeight,
            mapOf(
                EncodeHintType.MARGIN to 1
            )
        )
        val array = IntArray(width * barcodeHeight) { idx ->
            val isSet = matrix.get(idx % width, idx / width)
            return@IntArray if (isSet) Color.BLACK else Color.WHITE
        }
        return saveBitmapFromArray(barcodeFileName, array, width, barcodeHeight)
    }

    private fun setQrCode(id: String): Bitmap {
        val writer = QRCodeWriter()
        val matrix = writer.encode(
            id,
            BarcodeFormat.QR_CODE,
            width,
            qrCodeHeight,
            // Set max error correction level to achieve most redundancy
            mapOf(
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
                EncodeHintType.MARGIN to 1
            )
        )
        val array = IntArray(width * qrCodeHeight) { idx ->
            val isSet = matrix.get(idx % width, idx / width)
            return@IntArray if (isSet) Color.BLACK else Color.WHITE
        }
        return saveBitmapFromArray(qrCodeFileName, array, width, qrCodeHeight)
    }

    private fun saveBitmapFromArray(
        name: String,
        array: IntArray,
        width: Int,
        height: Int,
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(array, width, height, Bitmap.Config.RGB_565)
        appContext.deleteFile(name)
        val os = appContext.openFileOutput(name, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()
        os.flush()
        return bitmap
    }

    companion object {
        private val ATHLETE_ID_KEY = stringPreferencesKey("ATHLETE_ID_KEY")
    }
}
