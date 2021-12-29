package com.example.animeapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animeapp.presentation.components.StarType.*
import com.example.animeapp.ui.theme.LARGE_PADDING
import com.example.animeapp.ui.theme.LightGray
import com.example.animeapp.ui.theme.Star
import com.example.animeapp.util.Constants.STAR_PATH

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = LARGE_PADDING
) {

    val result = calculateStars(rating = rating)

    val starPath by remember {
        mutableStateOf(
            PathParser().parsePathString(pathData = STAR_PATH).toPath()
        )
    }

    val starPathBounds by remember { mutableStateOf(starPath.getBounds()) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = spaceBetween)
    ) {
        result[FILLED]?.let {
            repeat(it) {
                FilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result[HALF_FILLED]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result[EMPTY]?.let {
            repeat(it) {
                EmptyStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {

        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Star
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {

        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = Star,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {

        val canvasSize = size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun calculateStars(rating: Double): Map<StarType, Int> {
    val maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString().split(".").map { it.toInt() }

        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) {
                halfFilledStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        }
    }
    emptyStars = maxStars - (filledStars + halfFilledStars)

    return mapOf(
        FILLED to filledStars,
        HALF_FILLED to halfFilledStars,
        EMPTY to emptyStars
    )
}

data class StarPreview(
    val starPath: Path = PathParser().parsePathString(pathData = STAR_PATH).toPath(),
    val starPathBounds: Rect = starPath.getBounds(),
    val scaleFactor: Float
)

class StarPreviewParameterProvider : PreviewParameterProvider<StarPreview> {
    override val values = sequenceOf(
        StarPreview(scaleFactor = 1f),
        StarPreview(scaleFactor = 2f),
        StarPreview(scaleFactor = 3f)
    )

}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview(
    @PreviewParameter(StarPreviewParameterProvider::class) starPreview: StarPreview
) {
    FilledStar(
        starPath = starPreview.starPath,
        starPathBounds = starPreview.starPathBounds,
        scaleFactor = starPreview.scaleFactor
    )
}

@Preview(showBackground = true)
@Composable
fun HalfFilledStarPreview(
    @PreviewParameter(StarPreviewParameterProvider::class) starPreview: StarPreview
) {
    HalfFilledStar(
        starPath = starPreview.starPath,
        starPathBounds = starPreview.starPathBounds,
        scaleFactor = starPreview.scaleFactor
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyStarPreview(
    @PreviewParameter(StarPreviewParameterProvider::class) starPreview: StarPreview
) {
    EmptyStar(
        starPath = starPreview.starPath,
        starPathBounds = starPreview.starPathBounds,
        scaleFactor = starPreview.scaleFactor
    )
}

class RatingWidgetPreviewParameterProvider : PreviewParameterProvider<Double> {
    override val values = sequenceOf(0.1, 0.6, 1.0, 1.1, 1.6, 4.4, 4.9, 5.1, 6.0)

}

@Preview(showBackground = true)
@Composable
fun RatingWidgetPreview(
    @PreviewParameter(RatingWidgetPreviewParameterProvider::class) rating: Double
) {
    RatingWidget(modifier = Modifier.padding(all = LARGE_PADDING), rating = rating)
}

enum class StarType {
    FILLED,
    HALF_FILLED,
    EMPTY
}