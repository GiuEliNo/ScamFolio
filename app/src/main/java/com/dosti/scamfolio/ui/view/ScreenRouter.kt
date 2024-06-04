package com.dosti.scamfolio.ui.view

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.dosti.scamfolio.R

sealed class ScreenRouter(val route : String, @StringRes val label: Int, val icon: ImageVector) {
    companion object {
        val screens = listOf(
            PersonalArea,
            SearchCoin,
            Calculator
        )

        const val ROUTE_PERSONALAREA = "personalArea"
        const val ROUTE_SEARCHCOIN = "searchCoin"
        const val ROUTE_CALCULATOR = "calculator"
        const val COIN_DETAIL = "coinName"
    }

    private data object PersonalArea : ScreenRouter(
        ROUTE_PERSONALAREA,
        R.string.personal_area,
        Icons.Default.AccountCircle
    )

    private data object  SearchCoin : ScreenRouter(
        ROUTE_SEARCHCOIN,
        R.string.search_cryptos,
        Icons.Default.Search
    )

    private data object  Calculator : ScreenRouter(
        ROUTE_CALCULATOR,
        R.string.calculator,
        Icons.Default.Calculate
    )
}