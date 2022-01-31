package com.example.animeapp.presentation.screens.welcome

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.animeapp.R
import com.example.animeapp.domain.model.OnBoardingPage
import com.example.animeapp.navigation.Screen
import com.example.animeapp.ui.theme.*
import com.google.accompanist.pager.*

val onBoardingPages = listOf(
    OnBoardingPage.First,
    OnBoardingPage.Second,
    OnBoardingPage.Third,
)

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    welcomeScreenViewModel: WelcomeScreenViewModel = hiltViewModel()
) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = onBoardingPages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { pageIndex ->
            PagerScreen(onBoardingPage = onBoardingPages[pageIndex])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.activeIndicatorColor,
            inactiveColor = MaterialTheme.colors.inactiveIndicatorColor,
            indicatorWidth = PAGER_INDICATOR_WIDTH,
            spacing = PAGER_INDICATOR_SPACING
        )
        FinishButton(
            pagerState = pagerState,
            modifier = Modifier.weight(2f)
        ) {
            welcomeScreenNavigation(navHostController, welcomeScreenViewModel)
        }
    }
}

private fun welcomeScreenNavigation(
    navHostController: NavHostController,
    welcomeScreenViewModel: WelcomeScreenViewModel
) {
    navHostController.popBackStack()
    navHostController.navigate(Screen.Home.route)
    welcomeScreenViewModel.saveOnBoardingState(completed = true)
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

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == onBoardingPages.size - 1
        ) {
            Button(
                onClick = onClick, colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.finish))
            }
        }
    }
}

class OnBoardingPreviewParameterProvider : PreviewParameterProvider<OnBoardingPage> {
    override val values = onBoardingPages.asSequence()
}

@Preview(showBackground = true)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PagerScreenPreview(
    @PreviewParameter(OnBoardingPreviewParameterProvider::class) onBoardingPage: OnBoardingPage
) {
    PagerScreen(onBoardingPage = onBoardingPage)
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun FinishButtonPreview() =
    FinishButton(
        modifier = Modifier,
        pagerState = PagerState(onBoardingPages.size - 1)
    ) {}


