package com.example.animeapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import com.example.animeapp.ui.theme.LightGray
import com.example.animeapp.ui.theme.Star
import com.example.animeapp.util.Constants.STAR_PATH

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f
) {
    val starPath = remember {
        PathParser().parsePathString(pathData = STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    FilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = scaleFactor
    )
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {

        val canvasSize = this.size

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

        val canvasSize = this.size

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

        val canvasSize = this.size

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

data class StarPreview(
    val starPath: Path = PathParser().parsePathString(pathData = STAR_PATH).toPath(),
    val starPathBounds: Rect = starPath.getBounds(),
    val scaleFactor: Float
)

class RatingPreviewParameterProvider : PreviewParameterProvider<StarPreview> {
    override val values = sequenceOf(
        StarPreview(scaleFactor = 1f),
        StarPreview(scaleFactor = 2f),
        StarPreview(scaleFactor = 3f)
    )

}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview(
    @PreviewParameter(RatingPreviewParameterProvider::class) starPreview: StarPreview
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
    @PreviewParameter(RatingPreviewParameterProvider::class) starPreview: StarPreview
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
    @PreviewParameter(RatingPreviewParameterProvider::class) starPreview: StarPreview
) {
    EmptyStar(
        starPath = starPreview.starPath,
        starPathBounds = starPreview.starPathBounds,
        scaleFactor = starPreview.scaleFactor
    )
}