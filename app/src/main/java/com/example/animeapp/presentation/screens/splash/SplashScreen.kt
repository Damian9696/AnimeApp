package com.example.animeapp.presentation.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.animeapp.R
import com.example.animeapp.ui.theme.Purple500
import com.example.animeapp.ui.theme.Purple700
import com.example.animeapp.util.Constants.DURATION_SECOND
import com.example.animeapp.util.Constants.ROTATE_360
import com.example.animeapp.util.Constants.SMALL_DELAY

@Composable
fun SplashScreen(navHostController: NavHostController) {

    val degrees = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        degrees.animateTo(
            targetValue = ROTATE_360,
            animationSpec = tween(
                durationMillis = DURATION_SECOND,
                delayMillis = SMALL_DELAY
            )
        )
    }

    Splash(degrees = degrees.value)
}

@Composable
fun Splash(degrees: Float) {

    val backgroundModifier =
        if (isSystemInDarkTheme()) Modifier.background(Color.Black) else Modifier.background(
            Brush.verticalGradient(
                listOf(Purple700, Purple500)
            )
        )

    Box(
        modifier = backgroundModifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = degrees),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(
                R.string.app_logo
            )
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashPreview() = Splash(degrees = 0f)
