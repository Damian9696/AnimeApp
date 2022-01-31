package com.example.animeapp.presentation.common

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.animeapp.R
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.ui.theme.NETWORK_ERROR_ICON_SIZE
import com.example.animeapp.ui.theme.SMALL_PADDING
import com.example.animeapp.ui.theme.errorContentColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun InformationScreen(
    error: LoadState.Error? = null,
    context: Context = LocalContext.current,
    heroes: LazyPagingItems<Hero>? = null
) {
    var message by remember {
        mutableStateOf(
            "Find your Favorite Hero!"
        )
    }
    var icon by remember { mutableStateOf(R.drawable.ic_search_document) }

    error?.let {
        message = parseErrorMessage(
            context = context,
            error = error
        )
        icon = R.drawable.ic_network_error
    }

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    InformationContent(
        alphaAnimation = alphaAnimation,
        icon = icon,
        message = message,
        heroes = heroes,
        error = error
    )
}

@Composable
private fun InformationContent(
    alphaAnimation: Float,
    @DrawableRes icon: Int,
    message: String,
    heroes: LazyPagingItems<Hero>? = null,
    error: LoadState.Error? = null,
) {

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val scrollState = rememberScrollState()

    SwipeRefresh(
        swipeEnabled = error != null,
        state = refreshState,
        onRefresh = {
            isRefreshing = true
            heroes?.refresh()
            isRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_SIZE)
                    .alpha(alpha = alphaAnimation),
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error_icon),
                tint = MaterialTheme.colors.errorContentColor
            )
            Text(
                modifier = Modifier
                    .padding(all = SMALL_PADDING)
                    .alpha(alpha = alphaAnimation),
                text = message,
                color = MaterialTheme.colors.errorContentColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}

fun parseErrorMessage(context: Context, error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            context.getString(R.string.server_unavailable)
        }
        is ConnectException -> {
            context.getString(R.string.internet_unavailable)
        }
        else -> {
            context.getString(R.string.unknown_error)
        }
    }
}

class EmptyScreenParameterProvider : PreviewParameterProvider<LoadState.Error> {
    override val values = sequenceOf(
        LoadState.Error(SocketTimeoutException()),
        LoadState.Error(ConnectException())
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ErrorContentPreview(
    @PreviewParameter(EmptyScreenParameterProvider::class) error: LoadState.Error
) {
    InformationContent(
        alphaAnimation = ContentAlpha.disabled,
        icon = R.drawable.ic_network_error,
        message = parseErrorMessage(
            context = LocalContext.current,
            error = error
        )
    )
}