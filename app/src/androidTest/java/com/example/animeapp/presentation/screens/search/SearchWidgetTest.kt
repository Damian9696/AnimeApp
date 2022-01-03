package com.example.animeapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.animeapp.util.Constants.CLOSE_BUTTON
import com.example.animeapp.util.Constants.SEARCH_WIDGET
import com.example.animeapp.util.Constants.TEXT_FIELD
import org.junit.Rule
import org.junit.Test

class SearchWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchWidget(
                text = text.value,
                onTextChange = { text.value = it },
                onSearchClicked = {},
                onCloseClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(TEXT_FIELD)
            .performTextInput(TEST_INPUT)
        composeTestRule.onNodeWithContentDescription(TEXT_FIELD)
            .assertTextEquals(TEST_INPUT)
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButton_assertEmptyInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchWidget(
                text = text.value,
                onTextChange = { text.value = it },
                onSearchClicked = {},
                onCloseClicked = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(TEXT_FIELD)
            .performTextInput(TEST_INPUT)
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(TEXT_FIELD)
            .assertTextContains(EMPTY_INPUT)
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButtonTwice_assertCloseState() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchWidget(
                    text = text.value,
                    onTextChange = { text.value = it },
                    onSearchClicked = {},
                    onCloseClicked = { searchWidgetShown.value = false }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(TEXT_FIELD)
            .performTextInput(TEST_INPUT)
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(SEARCH_WIDGET)
            .assertDoesNotExist()
    }

    @Test
    fun openSearchWidget_pressCloseButtonWhenInputIsEmpty_assertCloseState() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchWidget(
                    text = text.value,
                    onTextChange = { text.value = it },
                    onSearchClicked = {},
                    onCloseClicked = { searchWidgetShown.value = false }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(SEARCH_WIDGET)
            .assertDoesNotExist()
    }

    companion object {
        const val TEST_INPUT = "Test"
        const val EMPTY_INPUT = ""
    }
}