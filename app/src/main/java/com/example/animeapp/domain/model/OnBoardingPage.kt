package com.example.animeapp.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.animeapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int
) {
    object First : OnBoardingPage(
        image = R.drawable.greetings,
        title = R.string.on_boarding_title_first,
        description = R.string.on_boarding_description_first
    )

    object Second : OnBoardingPage(
        image = R.drawable.explore,
        title = R.string.on_boarding_title_second,
        description = R.string.on_boarding_description_second
    )

    object Third : OnBoardingPage(
        image = R.drawable.power,
        title = R.string.on_boarding_title_third,
        description = R.string.on_boarding_description_third
    )
}
