package com.example.animeapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.animeapp.BuildConfig
import com.example.animeapp.R
import com.example.animeapp.domain.model.Hero
import com.example.animeapp.navigation.Screen
import com.example.animeapp.presentation.components.RatingWidget
import com.example.animeapp.presentation.components.ShimmerEffect
import com.example.animeapp.ui.theme.*
import com.example.animeapp.util.Constants.LOREM_IPSUM_LONG
import com.example.animeapp.util.Constants.LOREM_IPSUM_SHORT

@ExperimentalCoilApi
@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navHostController: NavHostController
) {

    val result = handlePagingResult(heroes = heroes)
    if (!result) return

    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(space = SMALL_PADDING)
    ) {
        items(items = heroes,
            key = { hero ->
                hero.id
            }
        ) { hero ->
            hero?.let {
                HeroItem(hero = it, navHostController = navHostController)
            }
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>
): Boolean {
    with(heroes.loadState) {
        val error = when {
            this.refresh is LoadState.Error -> this.refresh as LoadState.Error
            this.prepend is LoadState.Error -> this.prepend as LoadState.Error
            this.append is LoadState.Error -> this.append as LoadState.Error
            else -> null
        }

        return when {
            this.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                false
            }
            else -> true
        }
    }
}

@ExperimentalCoilApi
@Composable
fun HeroItem(
    hero: Hero,
    navHostController: NavHostController
) {

    val painter = rememberImagePainter(data = "${BuildConfig.BASE_URL}${hero.image}") {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navHostController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(bottomStart = LARGE_PADDING, bottomEnd = LARGE_PADDING)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        color = Color.White.copy(alpha = ContentAlpha.medium),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

class HeroPreviewParameterProvider : PreviewParameterProvider<Hero> {
    override val values = sequenceOf(
        Hero(
            id = 1,
            name = LOREM_IPSUM_SHORT,
            image = "",
            about = LOREM_IPSUM_SHORT,
            rating = 3.7,
            power = 0,
            month = "",
            day = "",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList()
        ),
        Hero(
            id = 1,
            name = LOREM_IPSUM_LONG,
            image = "",
            about = LOREM_IPSUM_LONG,
            rating = 5.0,
            power = 0,
            month = "",
            day = "",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList()
        )
    )

}

@ExperimentalCoilApi
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemPreview(
    @PreviewParameter(HeroPreviewParameterProvider::class) hero: Hero
) {
    HeroItem(
        hero = hero, navHostController = rememberNavController()
    )
}