package com.example.animeapp.presentation.screens.welcome

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavHostController
import com.example.animeapp.R
import com.example.animeapp.domain.model.OnBoardingPage
import com.example.animeapp.ui.theme.SMALL_PADDING
import com.example.animeapp.ui.theme.descriptionColor
import com.example.animeapp.ui.theme.titleColor
import com.example.animeapp.ui.theme.welcomeScreenBackgroundColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

val onBoardingPages = listOf(
    OnBoardingPage.First,
    OnBoardingPage.Second,
    OnBoardingPage.Third,
)

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(navHostController: NavHostController) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        HorizontalPager(
            count = onBoardingPages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            PagerScreen(onBoardingPage = onBoardingPages[pageIndex])
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = stringResource(
                R.string.on_boarding_image
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = onBoardingPage.title),
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SMALL_PADDING),
            text = stringResource(id = onBoardingPage.description),
            color = MaterialTheme.colors.descriptionColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

class OnBoardingPreviewParameterProvider : PreviewParameterProvider<OnBoardingPage> {
    override val values = onBoardingPages.asSequence()
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PagerScreenPreview(
    @PreviewParameter(OnBoardingPreviewParameterProvider::class) onBoardingPage: OnBoardingPage
) {
    PagerScreen(onBoardingPage = onBoardingPage)
}

