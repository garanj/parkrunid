package com.garan.parkrunid.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState


@OptIn(ExperimentalHorologistApi::class)
@Composable
fun TileEducationScreen(
    scalingLazyColumnState: ScalingLazyColumnState
) {
    val bullet = "\u2022"
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    ScalingLazyColumn(
        columnState = scalingLazyColumnState,
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Text(
                text = "Adding the Tile",
                style = MaterialTheme.typography.title2
            )
        }

        item {
            val explanationText = buildAnnotatedString {
                append("The easiest way to access your ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Parkrun ID")
                }
                append(" is to add the ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Tile")
                }
            }
            Text(explanationText)
        }

        item {
            Text(
                buildAnnotatedString {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t")
                        append("Navigate to the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("watch face")
                        }
                    }
                }
            )
        }

        item {
            Text(
                buildAnnotatedString {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t")
                        append("Swipe left to show your first ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tile")
                        }
                        append(" in the carousel.")
                    }
                }
            )
        }

        item {
            Text(
                buildAnnotatedString {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t")
                        append("Long-press on the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tile")
                        }
                        append(" to open the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Carousel editor")
                        }
                    }
                }
            )
        }

        item {
            Text(
                buildAnnotatedString {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t")
                        append("Locate the option to add a new ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tile")
                        }
                        append(" and select the ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Parkrun ID")
                        }
                        append(" tile.")
                    }
                }
            )
        }
    }
}