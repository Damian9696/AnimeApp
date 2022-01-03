package com.example.animeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import com.example.animeapp.ui.theme.SMALL_PADDING
import com.example.animeapp.util.Constants.EMPTY_STAR
import com.example.animeapp.util.Constants.FILLED_STAR
import com.example.animeapp.util.Constants.HALF_FILLED_STAR
import org.junit.Rule
import org.junit.Test

class RatingWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun passZeroPointZeroValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.0
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(0)

    }

    @Test
    fun passZeroPointFiveValue_Assert_OneHalfFilledStarAndFourEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.5
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(0)

    }

    @Test
    fun passZeroPointSixValue_Assert_OneFilledStar_And_FourEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.6
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(1)

    }

    @Test
    fun passTwoPointFiveValue_Assert_TwoFilledStar_OneHalfFilledStar_And_TwoEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 2.5
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(2)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(2)

    }

    @Test
    fun passInvalidValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 6.0
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(0)

    }

    @Test
    fun passNegativeValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = -6.0
            )
        }
        composeTestRule.onAllNodesWithContentDescription(EMPTY_STAR)
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription(HALF_FILLED_STAR)
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription(FILLED_STAR)
            .assertCountEquals(0)

    }
}