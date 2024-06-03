package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import com.dosti.scamfolio.R
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.HomepageViewModel


@Composable
fun Welcome(
    viewModelStoreOwner: ViewModelStoreOwner,
    homepageViewModel: HomepageViewModel
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        TopLabel(username = "user")
        Spacer(modifier = Modifier.height(140.dp))

        BalanceText(balance = 3.5F)
        Transactions()
    }
}

@Composable
fun TopLabel(
    username: String
) {
    Text(
        text = stringResource(R.string.welcome_back),
        fontSize = 50.sp,
        fontFamily = custom,
        color = Color.White,
    )
    Text(
        text = username,
        fontSize = 50.sp,
        fontFamily = custom,
        color = Color.White,
    )
}

@Composable
fun BalanceText(
    balance : Float
) {
    Row {
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Current balance: $balance$",
                fontSize = 30.sp,
                fontFamily = custom,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Transactions: ",
                fontSize = 25.sp,
                fontFamily = custom,
                color = Color.White
            )
        }
    }
}

@Composable
fun Transactions() {
    /*
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        ) {

        }
    }

     */
}

@Composable
fun SingleTransaction() {

}